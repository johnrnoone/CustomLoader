Procedure to compile and run:

> cd ~/Development/Architecture/CustomLoader
> javac -d . *.java
> java classLoader/LoadClass


Notes:
The custom class load process can be described as follows:
Read the class file using an InputStream and ByteArrayOutputStream.  The class is 
executed by using the defineClass(), resolveClass(), then invoke() methods.
Invoke is a method in class java.lang.reflect.Method.
 
In the normal execution of these class loaders CustomClassLoader.findClass as
overloaded does not get called to locate MemCopy.class.  Instead MyClassLoader
overloads method loadClass() to load the class.  This is in contrast with
CustomClassLoader which has no loadClass() method.  
The output in the loadClass case looks like:

CustomLoader> java classLoader/LoadClass
LoadClass created new loader1 object using MyClassLoader
LoadClass created new loader2 object using CustomClassLoader
MyClassLoader.loadClass loading class: classLoader.MemCopy, resolve: false
MyClassLoader.loadClass loading class: java.lang.Object, resolve: false
MemCopy.memCopy1 class after creation: class classLoader.MemCopy
MemCopy.memCopy2 class after creation: class classLoader.MemCopy
MemCopy.bootMemCopy class after creation: class classLoader.MemCopy
MemCopy loaded through loader1 is NOT same as that loaded by System loader.
MemCopy loaded through bootstrap loader is the same as that loaded by System loader.
MemCopy loaded through loader2 is the same as that loaded by System loader.
Calling MemCopy.main loaded through loader1
MyClassLoader.loadClass loading class: java.lang.String, resolve: false
MyClassLoader.loadClass loading class: java.util.Arrays, resolve: false
MyClassLoader.loadClass loading class: java.lang.System, resolve: false
MyClassLoader.loadClass loading class: java.io.PrintStream, resolve: false
[1, 2, 3, 4]
[1, 2, 3, 4]
[1, 2, 3, 4]
Calling MemCopy.main loaded through loader2
[1, 2, 3, 4]
[1, 2, 3, 4]
[1, 2, 3, 4]
Calling MemCopy.main loaded through bootstrap loader
[1, 2, 3, 4]
[1, 2, 3, 4]
[1, 2, 3, 4]




However CustomClassLoader.findClass can be forced to execute by deleting MemCopy.class
from the classLoader/ directory (put it in ./).  Also modify findClass to find it in ./
Also modify LoadClass not to load memCopy1 (using MyClassLoader) or bootMemCopy (the
copy loaded by the bootstrap loader).  If all that is done the output looks like this:

CustomLoader> java classLoader/LoadClass
LoadClass created new loader1 object using MyClassLoader
LoadClass created new loader2 object using CustomClassLoader
CustomClassLoader.findClass checks for class classLoader.MemCopy
CustomClassLoader.findClass loads class using path ./MemCopy.class
CustomClassLoader.findClass creates class using define( classLoader.MemCopy)
CustomClassLoader.findClass resolves class using resolveClass()
MemCopy.memCopy2 class after creation: class classLoader.MemCopy
Calling MemCopy.main loaded through loader2
[1, 2, 3, 4]
[1, 2, 3, 4]
[1, 2, 3, 4]

The point being, once the class file is read in the class is executed by using the
defineClass(), resolveClass(), then invoke() methods. Invoke is a method in class
java.lang.reflect.Method.

