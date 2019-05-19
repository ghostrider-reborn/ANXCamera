.class public abstract Lcom/google/gson/TypeAdapter;
.super Ljava/lang/Object;
.source "TypeAdapter.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "<T:",
        "Ljava/lang/Object;",
        ">",
        "Ljava/lang/Object;"
    }
.end annotation


# direct methods
.method public constructor <init>()V
    .registers 1

    .line 119
    .local p0, "this":Lcom/google/gson/TypeAdapter;, "Lcom/google/gson/TypeAdapter<TT;>;"
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final fromJson(Ljava/io/Reader;)Ljava/lang/Object;
    .registers 4
    .param p1, "in"    # Ljava/io/Reader;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/io/Reader;",
            ")TT;"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 255
    .local p0, "this":Lcom/google/gson/TypeAdapter;, "Lcom/google/gson/TypeAdapter<TT;>;"
    new-instance v0, Lcom/google/gson/stream/JsonReader;

    invoke-direct {v0, p1}, Lcom/google/gson/stream/JsonReader;-><init>(Ljava/io/Reader;)V

    .line 256
    .local v0, "reader":Lcom/google/gson/stream/JsonReader;
    invoke-virtual {p0, v0}, Lcom/google/gson/TypeAdapter;->read(Lcom/google/gson/stream/JsonReader;)Ljava/lang/Object;

    move-result-object v1

    return-object v1
.end method

.method public final fromJson(Ljava/lang/String;)Ljava/lang/Object;
    .registers 3
    .param p1, "json"    # Ljava/lang/String;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")TT;"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 269
    .local p0, "this":Lcom/google/gson/TypeAdapter;, "Lcom/google/gson/TypeAdapter<TT;>;"
    new-instance v0, Ljava/io/StringReader;

    invoke-direct {v0, p1}, Ljava/io/StringReader;-><init>(Ljava/lang/String;)V

    invoke-virtual {p0, v0}, Lcom/google/gson/TypeAdapter;->fromJson(Ljava/io/Reader;)Ljava/lang/Object;

    move-result-object v0

    return-object v0
.end method

.method public final fromJsonTree(Lcom/google/gson/JsonElement;)Ljava/lang/Object;
    .registers 4
    .param p1, "jsonTree"    # Lcom/google/gson/JsonElement;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/google/gson/JsonElement;",
            ")TT;"
        }
    .end annotation

    .line 280
    .local p0, "this":Lcom/google/gson/TypeAdapter;, "Lcom/google/gson/TypeAdapter<TT;>;"
    :try_start_0
    new-instance v0, Lcom/google/gson/internal/bind/JsonTreeReader;

    invoke-direct {v0, p1}, Lcom/google/gson/internal/bind/JsonTreeReader;-><init>(Lcom/google/gson/JsonElement;)V

    .line 281
    .local v0, "jsonReader":Lcom/google/gson/stream/JsonReader;
    invoke-virtual {p0, v0}, Lcom/google/gson/TypeAdapter;->read(Lcom/google/gson/stream/JsonReader;)Ljava/lang/Object;

    move-result-object v1
    :try_end_9
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_9} :catch_a

    return-object v1

    .line 282
    .end local v0    # "jsonReader":Lcom/google/gson/stream/JsonReader;
    :catch_a
    move-exception v0

    .line 283
    .local v0, "e":Ljava/io/IOException;
    new-instance v1, Lcom/google/gson/JsonIOException;

    invoke-direct {v1, v0}, Lcom/google/gson/JsonIOException;-><init>(Ljava/lang/Throwable;)V

    throw v1
.end method

.method public final nullSafe()Lcom/google/gson/TypeAdapter;
    .registers 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Lcom/google/gson/TypeAdapter<",
            "TT;>;"
        }
    .end annotation

    .line 186
    .local p0, "this":Lcom/google/gson/TypeAdapter;, "Lcom/google/gson/TypeAdapter<TT;>;"
    new-instance v0, Lcom/google/gson/TypeAdapter$1;

    invoke-direct {v0, p0}, Lcom/google/gson/TypeAdapter$1;-><init>(Lcom/google/gson/TypeAdapter;)V

    return-object v0
.end method

.method public abstract read(Lcom/google/gson/stream/JsonReader;)Ljava/lang/Object;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/google/gson/stream/JsonReader;",
            ")TT;"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation
.end method

.method public final toJson(Ljava/lang/Object;)Ljava/lang/String;
    .registers 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(TT;)",
            "Ljava/lang/String;"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 215
    .local p0, "this":Lcom/google/gson/TypeAdapter;, "Lcom/google/gson/TypeAdapter<TT;>;"
    .local p1, "value":Ljava/lang/Object;, "TT;"
    new-instance v0, Ljava/io/StringWriter;

    invoke-direct {v0}, Ljava/io/StringWriter;-><init>()V

    .line 216
    .local v0, "stringWriter":Ljava/io/StringWriter;
    invoke-virtual {p0, v0, p1}, Lcom/google/gson/TypeAdapter;->toJson(Ljava/io/Writer;Ljava/lang/Object;)V

    .line 217
    invoke-virtual {v0}, Ljava/io/StringWriter;->toString()Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method

.method public final toJson(Ljava/io/Writer;Ljava/lang/Object;)V
    .registers 4
    .param p1, "out"    # Ljava/io/Writer;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/io/Writer;",
            "TT;)V"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 141
    .local p0, "this":Lcom/google/gson/TypeAdapter;, "Lcom/google/gson/TypeAdapter<TT;>;"
    .local p2, "value":Ljava/lang/Object;, "TT;"
    new-instance v0, Lcom/google/gson/stream/JsonWriter;

    invoke-direct {v0, p1}, Lcom/google/gson/stream/JsonWriter;-><init>(Ljava/io/Writer;)V

    .line 142
    .local v0, "writer":Lcom/google/gson/stream/JsonWriter;
    invoke-virtual {p0, v0, p2}, Lcom/google/gson/TypeAdapter;->write(Lcom/google/gson/stream/JsonWriter;Ljava/lang/Object;)V

    .line 143
    return-void
.end method

.method public final toJsonTree(Ljava/lang/Object;)Lcom/google/gson/JsonElement;
    .registers 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(TT;)",
            "Lcom/google/gson/JsonElement;"
        }
    .end annotation

    .line 229
    .local p0, "this":Lcom/google/gson/TypeAdapter;, "Lcom/google/gson/TypeAdapter<TT;>;"
    .local p1, "value":Ljava/lang/Object;, "TT;"
    :try_start_0
    new-instance v0, Lcom/google/gson/internal/bind/JsonTreeWriter;

    invoke-direct {v0}, Lcom/google/gson/internal/bind/JsonTreeWriter;-><init>()V

    .line 230
    .local v0, "jsonWriter":Lcom/google/gson/internal/bind/JsonTreeWriter;
    invoke-virtual {p0, v0, p1}, Lcom/google/gson/TypeAdapter;->write(Lcom/google/gson/stream/JsonWriter;Ljava/lang/Object;)V

    .line 231
    invoke-virtual {v0}, Lcom/google/gson/internal/bind/JsonTreeWriter;->get()Lcom/google/gson/JsonElement;

    move-result-object v1
    :try_end_c
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_c} :catch_d

    return-object v1

    .line 232
    .end local v0    # "jsonWriter":Lcom/google/gson/internal/bind/JsonTreeWriter;
    :catch_d
    move-exception v0

    .line 233
    .local v0, "e":Ljava/io/IOException;
    new-instance v1, Lcom/google/gson/JsonIOException;

    invoke-direct {v1, v0}, Lcom/google/gson/JsonIOException;-><init>(Ljava/lang/Throwable;)V

    throw v1
.end method

.method public abstract write(Lcom/google/gson/stream/JsonWriter;Ljava/lang/Object;)V
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/google/gson/stream/JsonWriter;",
            "TT;)V"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation
.end method