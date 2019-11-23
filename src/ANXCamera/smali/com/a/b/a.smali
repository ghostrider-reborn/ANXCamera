.class public Lcom/a/b/a;
.super Ljava/lang/Object;
.source "TextMeshUtils.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private static a(Landroid/graphics/Canvas;Ljava/lang/String;FFLandroid/graphics/Paint;I)I
    .locals 17

    move-object/from16 v0, p0

    move-object/from16 v1, p1

    move/from16 v2, p3

    move-object/from16 v3, p4

    new-instance v4, Landroid/graphics/Rect;

    invoke-direct {v4}, Landroid/graphics/Rect;-><init>()V

    nop

    sget-object v5, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string/jumbo v6, "the text space is: %d\n"

    const/4 v7, 0x1

    new-array v8, v7, [Ljava/lang/Object;

    invoke-static/range {p5 .. p5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    const/4 v10, 0x0

    aput-object v9, v8, v10

    invoke-virtual {v5, v6, v8}, Ljava/io/PrintStream;->printf(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;

    sget-object v5, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string/jumbo v6, "the text length is: %d\n"

    new-array v8, v7, [Ljava/lang/Object;

    invoke-virtual/range {p1 .. p1}, Ljava/lang/String;->length()I

    move-result v9

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    aput-object v9, v8, v10

    invoke-virtual {v5, v6, v8}, Ljava/io/PrintStream;->printf(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;

    move/from16 v8, p2

    move v5, v10

    move v6, v5

    :goto_0
    invoke-virtual/range {p1 .. p1}, Ljava/lang/String;->length()I

    move-result v9

    if-ge v5, v9, :cond_6

    invoke-virtual {v1, v5}, Ljava/lang/String;->charAt(I)C

    move-result v9

    invoke-static {v9}, Ljava/lang/String;->valueOf(C)Ljava/lang/String;

    move-result-object v11

    invoke-static {v9}, Ljava/lang/String;->valueOf(C)Ljava/lang/String;

    move-result-object v12

    invoke-virtual {v3, v12}, Landroid/graphics/Paint;->measureText(Ljava/lang/String;)F

    move-result v12

    invoke-static {v12}, Ljava/lang/Math;->round(F)I

    nop

    const v12, 0xd800

    if-le v9, v12, :cond_1

    add-int/lit8 v5, v5, 0x1

    new-instance v11, Ljava/lang/StringBuilder;

    invoke-direct {v11}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {v9}, Ljava/lang/String;->valueOf(C)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v11, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v5}, Ljava/lang/String;->charAt(I)C

    move-result v9

    invoke-virtual {v11, v9}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    invoke-virtual {v11}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    if-ne v5, v7, :cond_0

    goto :goto_1

    :cond_0
    add-int/lit8 v11, v5, 0x1

    invoke-virtual {v3, v1, v5, v11, v4}, Landroid/graphics/Paint;->getTextBounds(Ljava/lang/String;IILandroid/graphics/Rect;)V

    invoke-virtual {v4}, Landroid/graphics/Rect;->width()I

    :goto_1
    invoke-virtual {v4}, Landroid/graphics/Rect;->width()I

    move-result v11

    div-int/lit8 v12, p5, 0x2

    add-int/2addr v11, v12

    int-to-float v12, v11

    add-float/2addr v8, v12

    add-int/2addr v6, v11

    if-eqz v0, :cond_5

    invoke-virtual {v0, v9, v8, v2, v3}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    goto/16 :goto_5

    :cond_1
    const/16 v12, 0x20

    if-ne v9, v12, :cond_2

    invoke-virtual {v1, v5}, Ljava/lang/String;->charAt(I)C

    move-result v9

    invoke-static {v9}, Ljava/lang/String;->valueOf(C)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v3, v9}, Landroid/graphics/Paint;->measureText(Ljava/lang/String;)F

    move-result v9

    invoke-static {v9}, Ljava/lang/Math;->round(F)I

    move-result v9

    add-int v9, v9, p5

    goto :goto_4

    :cond_2
    add-int/lit8 v12, v5, 0x1

    invoke-virtual {v3, v1, v5, v12, v4}, Landroid/graphics/Paint;->getTextBounds(Ljava/lang/String;IILandroid/graphics/Rect;)V

    const/16 v12, 0x80

    if-ge v9, v12, :cond_4

    invoke-static {v11}, Lcom/a/b/a;->f(Ljava/lang/String;)Z

    move-result v11

    if-eqz v11, :cond_3

    invoke-virtual {v4}, Landroid/graphics/Rect;->width()I

    move-result v11

    add-int v11, v11, p5

    sget-object v12, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v13, "%s is NumOrLetters\n"

    new-array v14, v7, [Ljava/lang/Object;

    invoke-static {v9}, Ljava/lang/Character;->valueOf(C)Ljava/lang/Character;

    move-result-object v9

    aput-object v9, v14, v10

    invoke-virtual {v12, v13, v14}, Ljava/io/PrintStream;->printf(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;

    goto :goto_2

    :cond_3
    invoke-virtual {v4}, Landroid/graphics/Rect;->width()I

    move-result v9

    mul-int/lit8 v11, p5, 0x2

    add-int/2addr v9, v11

    goto :goto_3

    :cond_4
    invoke-virtual {v4}, Landroid/graphics/Rect;->width()I

    move-result v11

    div-int/lit8 v12, p5, 0x2

    add-int/2addr v11, v12

    sget-object v12, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v13, "%s is not NumOrLetters\n"

    new-array v14, v7, [Ljava/lang/Object;

    invoke-static {v9}, Ljava/lang/Character;->valueOf(C)Ljava/lang/Character;

    move-result-object v9

    aput-object v9, v14, v10

    invoke-virtual {v12, v13, v14}, Ljava/io/PrintStream;->printf(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;

    :goto_2
    move v9, v11

    :goto_3
    sget-object v11, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v12, "%s is: %d, space is: %d, text is %d\n"

    const/4 v13, 0x4

    new-array v13, v13, [Ljava/lang/Object;

    invoke-virtual {v1, v5}, Ljava/lang/String;->charAt(I)C

    move-result v14

    invoke-static {v14}, Ljava/lang/Character;->valueOf(C)Ljava/lang/Character;

    move-result-object v14

    aput-object v14, v13, v10

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    aput-object v14, v13, v7

    invoke-static/range {p5 .. p5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v14

    const/4 v15, 0x2

    aput-object v14, v13, v15

    const/4 v14, 0x3

    invoke-virtual {v4}, Landroid/graphics/Rect;->width()I

    move-result v10

    invoke-static {v10}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v10

    aput-object v10, v13, v14

    invoke-virtual {v11, v12, v13}, Ljava/io/PrintStream;->printf(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;

    :goto_4
    int-to-float v10, v9

    add-float/2addr v8, v10

    add-int/2addr v6, v9

    if-eqz v0, :cond_5

    invoke-virtual {v1, v5}, Ljava/lang/String;->charAt(I)C

    move-result v9

    invoke-static {v9}, Ljava/lang/String;->valueOf(C)Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v0, v9, v8, v2, v3}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    :cond_5
    :goto_5
    add-int/2addr v5, v7

    const/4 v10, 0x0

    goto/16 :goto_0

    :cond_6
    return v6
.end method

.method private static a([BLjava/lang/String;IIIFIFIFFFIIII)Landroid/graphics/Bitmap;
    .locals 20

    move/from16 v0, p2

    move/from16 v7, p4

    move/from16 v8, p13

    move/from16 v2, p14

    move/from16 v3, p15

    new-instance v9, Ljava/lang/String;

    move-object/from16 v4, p0

    invoke-direct {v9, v4}, Ljava/lang/String;-><init>([B)V

    new-instance v10, Landroid/graphics/Rect;

    const/4 v11, 0x0

    invoke-direct {v10, v11, v11, v2, v3}, Landroid/graphics/Rect;-><init>(IIII)V

    sget-object v4, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    invoke-static {v2, v3, v4}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object v12

    new-instance v2, Landroid/graphics/Paint;

    invoke-direct {v2}, Landroid/graphics/Paint;-><init>()V

    invoke-virtual {v2, v11}, Landroid/graphics/Paint;->setColor(I)V

    sget-object v3, Landroid/graphics/Paint$Style;->FILL:Landroid/graphics/Paint$Style;

    invoke-virtual {v2, v3}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    new-instance v13, Landroid/graphics/Canvas;

    invoke-direct {v13, v12}, Landroid/graphics/Canvas;-><init>(Landroid/graphics/Bitmap;)V

    new-instance v14, Landroid/text/TextPaint;

    invoke-direct {v14}, Landroid/text/TextPaint;-><init>()V

    move/from16 v2, p3

    invoke-virtual {v14, v2}, Landroid/text/TextPaint;->setColor(I)V

    int-to-float v15, v0

    invoke-virtual {v14, v15}, Landroid/text/TextPaint;->setTextSize(F)V

    const/4 v6, 0x1

    invoke-virtual {v14, v6}, Landroid/text/TextPaint;->setAntiAlias(Z)V

    sget-object v2, Landroid/graphics/Paint$Style;->FILL:Landroid/graphics/Paint$Style;

    invoke-virtual {v14, v2}, Landroid/text/TextPaint;->setStyle(Landroid/graphics/Paint$Style;)V

    if-nez v8, :cond_0

    sget-object v2, Landroid/graphics/Paint$Align;->LEFT:Landroid/graphics/Paint$Align;

    invoke-virtual {v14, v2}, Landroid/text/TextPaint;->setTextAlign(Landroid/graphics/Paint$Align;)V

    goto :goto_0

    :cond_0
    if-ne v8, v6, :cond_1

    sget-object v2, Landroid/graphics/Paint$Align;->CENTER:Landroid/graphics/Paint$Align;

    invoke-virtual {v14, v2}, Landroid/text/TextPaint;->setTextAlign(Landroid/graphics/Paint$Align;)V

    goto :goto_0

    :cond_1
    sget-object v2, Landroid/graphics/Paint$Align;->RIGHT:Landroid/graphics/Paint$Align;

    invoke-virtual {v14, v2}, Landroid/text/TextPaint;->setTextAlign(Landroid/graphics/Paint$Align;)V

    :goto_0
    and-int/lit8 v2, p6, 0x10

    const/16 v3, 0x10

    if-ne v2, v3, :cond_2

    invoke-virtual {v14, v6}, Landroid/text/TextPaint;->setUnderlineText(Z)V

    :cond_2
    and-int/lit8 v2, p6, 0x20

    const/16 v3, 0x20

    if-ne v2, v3, :cond_3

    invoke-virtual {v14, v6}, Landroid/text/TextPaint;->setStrikeThruText(Z)V

    :cond_3
    and-int/lit8 v2, p6, 0x4

    const/4 v3, 0x4

    if-ne v2, v3, :cond_4

    move/from16 v2, p5

    neg-float v2, v2

    const/high16 v3, 0x42b40000    # 90.0f

    div-float/2addr v2, v3

    invoke-virtual {v14, v2}, Landroid/text/TextPaint;->setTextSkewX(F)V

    :cond_4
    and-int/lit8 v2, p6, 0x8

    const/16 v3, 0x8

    if-ne v2, v3, :cond_5

    invoke-virtual {v14, v6}, Landroid/text/TextPaint;->setFakeBoldText(Z)V

    :cond_5
    and-int/lit8 v2, p6, 0x2

    const/4 v5, 0x2

    if-ne v2, v5, :cond_6

    move/from16 v2, p9

    move/from16 v3, p10

    move/from16 v4, p11

    move/from16 v5, p12

    invoke-virtual {v14, v2, v3, v4, v5}, Landroid/text/TextPaint;->setShadowLayer(FFFI)V

    :cond_6
    invoke-virtual {v14}, Landroid/text/TextPaint;->getFontMetrics()Landroid/graphics/Paint$FontMetrics;

    move-result-object v2

    iget v3, v2, Landroid/graphics/Paint$FontMetrics;->top:F

    iget v2, v2, Landroid/graphics/Paint$FontMetrics;->bottom:F

    invoke-virtual {v10}, Landroid/graphics/Rect;->centerY()I

    move-result v4

    int-to-float v4, v4

    const/high16 v5, 0x40000000    # 2.0f

    div-float/2addr v3, v5

    sub-float/2addr v4, v3

    div-float/2addr v2, v5

    sub-float/2addr v4, v2

    float-to-int v5, v4

    and-int/lit8 v1, p6, 0x1

    const/16 v3, 0x15

    if-ne v1, v6, :cond_e

    new-instance v2, Landroid/text/TextPaint;

    invoke-direct {v2}, Landroid/text/TextPaint;-><init>()V

    move/from16 v1, p8

    invoke-virtual {v2, v1}, Landroid/text/TextPaint;->setColor(I)V

    invoke-virtual {v14}, Landroid/text/TextPaint;->getTextSize()F

    move-result v1

    invoke-virtual {v2, v1}, Landroid/text/TextPaint;->setTextSize(F)V

    invoke-virtual {v14}, Landroid/text/TextPaint;->isAntiAlias()Z

    move-result v1

    invoke-virtual {v2, v1}, Landroid/text/TextPaint;->setAntiAlias(Z)V

    sget-object v1, Landroid/graphics/Paint$Style;->STROKE:Landroid/graphics/Paint$Style;

    invoke-virtual {v2, v1}, Landroid/text/TextPaint;->setStyle(Landroid/graphics/Paint$Style;)V

    const/high16 v1, 0x40a00000    # 5.0f

    mul-float v1, v1, p7

    div-float/2addr v1, v15

    invoke-virtual {v2, v1}, Landroid/text/TextPaint;->setStrokeWidth(F)V

    invoke-virtual {v14}, Landroid/text/TextPaint;->getTextAlign()Landroid/graphics/Paint$Align;

    move-result-object v1

    invoke-virtual {v2, v1}, Landroid/text/TextPaint;->setTextAlign(Landroid/graphics/Paint$Align;)V

    invoke-virtual {v14}, Landroid/text/TextPaint;->getTextSkewX()F

    move-result v1

    invoke-virtual {v2, v1}, Landroid/text/TextPaint;->setTextSkewX(F)V

    invoke-virtual {v14, v11}, Landroid/text/TextPaint;->setFakeBoldText(Z)V

    invoke-virtual {v2, v6}, Landroid/text/TextPaint;->setFakeBoldText(Z)V

    int-to-float v1, v7

    div-float/2addr v1, v15

    sget v4, Landroid/os/Build$VERSION;->SDK_INT:I

    if-lt v4, v3, :cond_9

    invoke-virtual {v2, v1}, Landroid/text/TextPaint;->setLetterSpacing(F)V

    if-nez v8, :cond_7

    iget v1, v10, Landroid/graphics/Rect;->left:I

    int-to-float v1, v1

    int-to-float v4, v5

    invoke-virtual {v13, v9, v1, v4, v2}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    goto/16 :goto_2

    :cond_7
    if-ne v8, v6, :cond_8

    invoke-virtual {v10}, Landroid/graphics/Rect;->centerX()I

    move-result v1

    int-to-float v1, v1

    int-to-float v4, v5

    invoke-virtual {v13, v9, v1, v4, v2}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    goto/16 :goto_2

    :cond_8
    iget v1, v10, Landroid/graphics/Rect;->right:I

    int-to-float v1, v1

    int-to-float v4, v5

    invoke-virtual {v13, v9, v1, v4, v2}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    goto/16 :goto_2

    :cond_9
    new-instance v1, Landroid/graphics/Rect;

    invoke-direct {v1}, Landroid/graphics/Rect;-><init>()V

    invoke-virtual {v9}, Ljava/lang/String;->length()I

    move-result v4

    invoke-virtual {v14, v9, v11, v4, v1}, Landroid/text/TextPaint;->getTextBounds(Ljava/lang/String;IILandroid/graphics/Rect;)V

    nop

    move v4, v11

    move/from16 v17, v4

    :goto_1
    invoke-virtual {v9}, Ljava/lang/String;->length()I

    move-result v3

    if-ge v4, v3, :cond_b

    invoke-virtual {v9, v4}, Ljava/lang/String;->charAt(I)C

    move-result v3

    const v11, 0xd800

    if-le v3, v11, :cond_a

    add-int/lit8 v4, v4, 0x1

    add-int/lit8 v17, v17, 0x1

    :cond_a
    add-int/2addr v4, v6

    const/16 v3, 0x15

    const/4 v11, 0x0

    goto :goto_1

    :cond_b
    const v11, 0xd800

    if-nez v8, :cond_c

    const/4 v3, 0x0

    int-to-float v4, v5

    move-object v1, v13

    move-object/from16 v18, v2

    move-object v2, v9

    const/16 v11, 0x15

    move v11, v5

    const/16 v16, 0x2

    move-object/from16 v5, v18

    move-object/from16 v19, v12

    move v12, v6

    move v6, v7

    invoke-static/range {v1 .. v6}, Lcom/a/b/a;->a(Landroid/graphics/Canvas;Ljava/lang/String;FFLandroid/graphics/Paint;I)I

    goto :goto_3

    :cond_c
    move-object/from16 v18, v2

    move v11, v5

    move-object/from16 v19, v12

    const/16 v16, 0x2

    move v12, v6

    if-ne v8, v12, :cond_d

    invoke-virtual {v10}, Landroid/graphics/Rect;->width()I

    move-result v2

    invoke-virtual {v1}, Landroid/graphics/Rect;->width()I

    move-result v1

    sub-int/2addr v2, v1

    mul-int v17, v17, v0

    add-int v2, v2, v17

    invoke-virtual {v9}, Ljava/lang/String;->length()I

    move-result v1

    sub-int/2addr v1, v12

    mul-int/2addr v1, v7

    sub-int/2addr v2, v1

    div-int/lit8 v2, v2, 0x2

    int-to-float v3, v2

    int-to-float v4, v11

    move-object v1, v13

    move-object v2, v9

    move-object/from16 v5, v18

    move v6, v7

    invoke-static/range {v1 .. v6}, Lcom/a/b/a;->a(Landroid/graphics/Canvas;Ljava/lang/String;FFLandroid/graphics/Paint;I)I

    goto :goto_3

    :cond_d
    invoke-virtual {v10}, Landroid/graphics/Rect;->width()I

    move-result v2

    invoke-virtual {v1}, Landroid/graphics/Rect;->width()I

    move-result v1

    sub-int/2addr v2, v1

    mul-int v17, v17, v0

    add-int v2, v2, v17

    invoke-virtual {v9}, Ljava/lang/String;->length()I

    move-result v1

    sub-int/2addr v1, v12

    mul-int/2addr v1, v7

    sub-int/2addr v2, v1

    int-to-float v3, v2

    int-to-float v4, v11

    move-object v1, v13

    move-object v2, v9

    move-object/from16 v5, v18

    move v6, v7

    invoke-static/range {v1 .. v6}, Lcom/a/b/a;->a(Landroid/graphics/Canvas;Ljava/lang/String;FFLandroid/graphics/Paint;I)I

    goto :goto_3

    :cond_e
    :goto_2
    move v11, v5

    move-object/from16 v19, v12

    const/16 v16, 0x2

    move v12, v6

    :goto_3
    int-to-float v1, v7

    div-float/2addr v1, v15

    sget v2, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x15

    if-lt v2, v3, :cond_11

    invoke-virtual {v14, v1}, Landroid/text/TextPaint;->setLetterSpacing(F)V

    if-nez v8, :cond_f

    iget v0, v10, Landroid/graphics/Rect;->left:I

    int-to-float v0, v0

    int-to-float v1, v11

    invoke-virtual {v13, v9, v0, v1, v14}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    goto/16 :goto_5

    :cond_f
    if-ne v8, v12, :cond_10

    invoke-virtual {v10}, Landroid/graphics/Rect;->centerX()I

    move-result v0

    int-to-float v0, v0

    int-to-float v1, v11

    invoke-virtual {v13, v9, v0, v1, v14}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    goto/16 :goto_5

    :cond_10
    iget v0, v10, Landroid/graphics/Rect;->right:I

    int-to-float v0, v0

    int-to-float v1, v11

    invoke-virtual {v13, v9, v0, v1, v14}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    goto/16 :goto_5

    :cond_11
    new-instance v1, Landroid/graphics/Rect;

    invoke-direct {v1}, Landroid/graphics/Rect;-><init>()V

    invoke-virtual {v9}, Ljava/lang/String;->length()I

    move-result v2

    const/4 v3, 0x0

    invoke-virtual {v14, v9, v3, v2, v1}, Landroid/text/TextPaint;->getTextBounds(Ljava/lang/String;IILandroid/graphics/Rect;)V

    nop

    move v2, v3

    :goto_4
    invoke-virtual {v9}, Ljava/lang/String;->length()I

    move-result v4

    if-ge v3, v4, :cond_13

    invoke-virtual {v9, v3}, Ljava/lang/String;->charAt(I)C

    move-result v4

    const v5, 0xd800

    if-le v4, v5, :cond_12

    add-int/lit8 v3, v3, 0x1

    add-int/lit8 v2, v2, 0x1

    :cond_12
    add-int/2addr v3, v12

    goto :goto_4

    :cond_13
    if-nez v8, :cond_14

    const/4 v2, 0x0

    int-to-float v3, v11

    move-object v0, v13

    move-object v1, v9

    move-object v4, v14

    move v5, v7

    invoke-static/range {v0 .. v5}, Lcom/a/b/a;->a(Landroid/graphics/Canvas;Ljava/lang/String;FFLandroid/graphics/Paint;I)I

    goto :goto_5

    :cond_14
    if-ne v8, v12, :cond_15

    invoke-virtual {v10}, Landroid/graphics/Rect;->width()I

    move-result v3

    invoke-virtual {v1}, Landroid/graphics/Rect;->width()I

    move-result v1

    sub-int/2addr v3, v1

    mul-int/2addr v2, v0

    add-int/2addr v3, v2

    invoke-virtual {v9}, Ljava/lang/String;->length()I

    move-result v0

    sub-int/2addr v0, v12

    mul-int/2addr v0, v7

    sub-int/2addr v3, v0

    div-int/lit8 v3, v3, 0x2

    int-to-float v2, v3

    int-to-float v3, v11

    move-object v0, v13

    move-object v1, v9

    move-object v4, v14

    move v5, v7

    invoke-static/range {v0 .. v5}, Lcom/a/b/a;->a(Landroid/graphics/Canvas;Ljava/lang/String;FFLandroid/graphics/Paint;I)I

    goto :goto_5

    :cond_15
    invoke-virtual {v10}, Landroid/graphics/Rect;->width()I

    move-result v3

    invoke-virtual {v1}, Landroid/graphics/Rect;->width()I

    move-result v1

    sub-int/2addr v3, v1

    mul-int/2addr v2, v0

    add-int/2addr v3, v2

    invoke-virtual {v9}, Ljava/lang/String;->length()I

    move-result v0

    sub-int/2addr v0, v12

    mul-int/2addr v0, v7

    sub-int/2addr v3, v0

    int-to-float v2, v3

    int-to-float v3, v11

    move-object v0, v13

    move-object v1, v9

    move-object v4, v14

    move v5, v7

    invoke-static/range {v0 .. v5}, Lcom/a/b/a;->a(Landroid/graphics/Canvas;Ljava/lang/String;FFLandroid/graphics/Paint;I)I

    :goto_5
    const/16 v0, 0x1f

    invoke-virtual {v13, v0}, Landroid/graphics/Canvas;->save(I)I

    invoke-virtual {v13}, Landroid/graphics/Canvas;->restore()V

    return-object v19
.end method

.method public static f(Ljava/lang/String;)Z
    .locals 1

    const-string v0, "^[A-Za-z0-9_]+$"

    invoke-static {v0}, Ljava/util/regex/Pattern;->compile(Ljava/lang/String;)Ljava/util/regex/Pattern;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/util/regex/Pattern;->matcher(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;

    move-result-object p0

    invoke-virtual {p0}, Ljava/util/regex/Matcher;->matches()Z

    move-result p0

    return p0
.end method
