package classLoader;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomClassLoader extends ClassLoader {

    private Map<String, Class<?>> classes;

    public CustomClassLoader() {
        super(CustomClassLoader.class.getClassLoader());
        classes = new HashMap<String, Class<?>>();
    }

    public String toString() {
        return CustomClassLoader.class.getName();
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {

       System.out.println("CustomClassLoader.findClass checks for class " + name);

        if (classes.containsKey(name)) {
           System.out.println("CustomClassLoader.findClass finds class " + name + " and returns");
            return classes.get(name);
        }

        String path = new String("./MemCopy.class");
//        String path = name.replace('.', File.separatorChar) + ".class";
        byte[] b = null;
        
        try {
           System.out.println("CustomClassLoader.findClass loads class using path " + path);
            b = loadClassData(path);
        } catch (IOException e) {
            throw new ClassNotFoundException("Class not found at path: " + new File(name).getAbsolutePath(), e);
        }

        System.out.println("CustomClassLoader.findClass creates class using define( " + name + ")");
        Class<?> c = defineClass(name, b, 0, b.length);
        System.out.println("CustomClassLoader.findClass resolves class using resolveClass()");
        resolveClass(c);
        classes.put(name, c);

        return c;
    }

    private byte[] loadClassData(String name) throws IOException {
        File file = new File(name);
        int size = (int)file.length();
        byte buff[] = new byte[size];
        DataInputStream in = new DataInputStream(new FileInputStream(name));
        in.readFully(buff);
        in.close();

        return buff;
    }

}
