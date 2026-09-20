.class public Output 
.super java/lang/Object

.method public <init>()V
 aload_0
 invokenonvirtual java/lang/Object/<init>()V
 return
.end method

.method public static print(I)V
 .limit stack 2
 getstatic java/lang/System/out Ljava/io/PrintStream;
 iload_0 
 invokestatic java/lang/Integer/toString(I)Ljava/lang/String;
 invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
 return
.end method

.method public static read()I
 .limit stack 3
 new java/util/Scanner
 dup
 getstatic java/lang/System/in Ljava/io/InputStream;
 invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
 invokevirtual java/util/Scanner/next()Ljava/lang/String;
 invokestatic java/lang/Integer.parseInt(Ljava/lang/String;)I
 ireturn
.end method

.method public static run()V
 .limit stack 1024
 .limit locals 256
 ldc 20
 istore 0
 ldc 70
 istore 1
 ldc 30
 istore 2
 ldc 2
 istore 3
L3:
 ldc 10
 istore 3
L4:
 iload 3
 ldc 3
 if_icmplt L5
 goto L6
L5:
 iload 1
 iload 2
 if_icmpne L8
 goto L9
L8:
 iload 1
 iload 2
 if_icmpne L12
 goto L13
L12:
 iload 1
 invokestatic Output/print(I)V
 iload 1
 invokestatic Output/print(I)V
 goto L14
L13:
 iload 1
 iload 2
 if_icmpeq L18
 goto L19
L18:
 ldc 10
 invokestatic Output/print(I)V
 goto L20
L19:
L21:
 ldc 9
 istore 1
L22:
 iload 1
 ldc 13
 if_icmpne L23
 goto L24
L23:
 iload 1
 ldc 9
 if_icmpeq L26
 goto L27
L26:
 iload 1
 invokestatic Output/print(I)V
 goto L28
L27:
 ldc 10
 invokestatic Output/print(I)V
 goto L28
L28:
 iload 1
 ldc 1
 iadd 
 istore 1
 goto L22
L24:
 goto L20
L20:
 goto L14
L14:
 goto L10
L9:
 iload 0
 invokestatic Output/print(I)V
 goto L10
L10:
 iload 3
 ldc 1
 iadd 
 istore 3
 goto L4
L6:
L0:
 return
.end method

.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

