package com.mojang.blaze3d.buffers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.jtracy.MemoryPool;
import com.mojang.jtracy.TracyClient;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GpuBuffer implements AutoCloseable {
    private static final MemoryPool MEMORY_POOl = TracyClient.createMemoryPool("GPU Buffers");
    private final BufferType type;
    private final BufferUsage usage;
    private boolean closed;
    private boolean initialized = false;
    public final int handle;
    public int size;

    public GpuBuffer(BufferType type, BufferUsage usage, int size) {
        this.type = type;
        this.size = size;
        this.usage = usage;
        this.handle = GlStateManager._glGenBuffers();
    }

    public GpuBuffer(BufferType type, BufferUsage usage, ByteBuffer buffer) {
        this(type, usage, buffer.remaining());
        this.write(buffer, 0);
    }

    public void resize(int size) {
        if (this.closed) {
            throw new IllegalStateException("Buffer already closed");
        } else {
            if (this.initialized) {
                MEMORY_POOl.free((long)this.handle);
            }

            this.size = size;
            if (this.usage.writable) {
                this.initialized = false;
            } else {
                this.bind();
                GlStateManager._glBufferData(this.type.id, (long)size, this.usage.id);
                MEMORY_POOl.malloc((long)this.handle, size);
                this.initialized = true;
            }
        }
    }

    public void write(ByteBuffer buffer, int offset) {
        if (this.closed) {
            throw new IllegalStateException("Buffer already closed");
        } else if (!this.usage.writable) {
            throw new IllegalStateException("Buffer is not writable");
        } else {
            int i = buffer.remaining();
            if (i + offset > this.size) {
                throw new IllegalArgumentException(
                    "Cannot write more data than this buffer can hold (attempting to write "
                        + i
                        + " bytes at offset "
                        + offset
                        + " to "
                        + this.size
                        + " size buffer)"
                );
            } else {
                this.bind();
                if (this.initialized) {
                    GlStateManager._glBufferSubData(this.type.id, offset, buffer);
                } else if (offset == 0 && i == this.size) {
                    GlStateManager._glBufferData(this.type.id, buffer, this.usage.id);
                    MEMORY_POOl.malloc((long)this.handle, this.size);
                    this.initialized = true;
                } else {
                    GlStateManager._glBufferData(this.type.id, (long)this.size, this.usage.id);
                    GlStateManager._glBufferSubData(this.type.id, offset, buffer);
                    MEMORY_POOl.malloc((long)this.handle, this.size);
                    this.initialized = true;
                }
            }
        }
    }

    @Nullable
    public GpuBuffer.ReadView read() {
        return this.read(0, this.size);
    }

    @Nullable
    public GpuBuffer.ReadView read(int offset, int length) {
        if (this.closed) {
            throw new IllegalStateException("Buffer already closed");
        } else if (!this.usage.readable) {
            throw new IllegalStateException("Buffer is not readable");
        } else if (offset + length > this.size) {
            throw new IllegalArgumentException(
                "Cannot read more data than this buffer can hold (attempting to read "
                    + length
                    + " bytes at offset "
                    + offset
                    + " from "
                    + this.size
                    + " size buffer)"
            );
        } else {
            this.bind();
            ByteBuffer bytebuffer = GlStateManager._glMapBufferRange(this.type.id, offset, length, 1);
            return bytebuffer == null ? null : new GpuBuffer.ReadView(this.type.id, bytebuffer);
        }
    }

    @Override
    public void close() {
        if (!this.closed) {
            this.closed = true;
            GlStateManager._glDeleteBuffers(this.handle);
            if (this.initialized) {
                MEMORY_POOl.free((long)this.handle);
            }
        }
    }

    public void bind() {
        GlStateManager._glBindBuffer(this.type.id, this.handle);
    }

    @OnlyIn(Dist.CLIENT)
    public static class ReadView implements AutoCloseable {
        private final int target;
        private final ByteBuffer data;

        protected ReadView(int target, ByteBuffer data) {
            this.target = target;
            this.data = data;
        }

        public ByteBuffer data() {
            return this.data;
        }

        @Override
        public void close() {
            GlStateManager._glUnmapBuffer(this.target);
        }
    }
}
