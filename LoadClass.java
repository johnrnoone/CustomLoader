package classLoader;

//import memCopy.MemCopy;

public class LoadClass
{


    public static void main(String[] args) throws Exception {
       
        MyClassLoader loader1 = new MyClassLoader();
        System.out.println("LoadClass created new loader1 object using MyClassLoader");
         
        CustomClassLoader loader2 = new CustomClassLoader();
        System.out.println("LoadClass created new loader2 object using CustomClassLoader");

        
        // load MemCopy  
        Class<?> memCopy1 = Class.forName("classLoader.MemCopy", true, loader1);
        System.out.println("MemCopy.memCopy1 class after creation: " + memCopy1);
        Class<?> memCopy2 = Class.forName("classLoader.MemCopy", true, loader2);
        System.out.println("MemCopy.memCopy2 class after creation: " + memCopy2);
        Class<?> bootMemCopy = Class.forName("classLoader.MemCopy");  // this loads using bootstrap loader
        System.out.println("MemCopy.bootMemCopy class after creation: " + bootMemCopy);

        if (MemCopy.class.equals(memCopy1)) {
            System.out.println("MemCopy loaded through loader1 is the same as that loaded by System loader.");
        }
        else {
            System.out.println("MemCopy loaded through loader1 is NOT same as that loaded by System loader.");
        } 
        

        if (MemCopy.class.equals(bootMemCopy)) {
           System.out.println("MemCopy loaded through bootstrap loader is the same as that loaded by System loader.");
       }
       else {
           System.out.println("MemCopy loaded through bootstrap loader is NOT same as that loaded by System loader.");
       } 
        
        if (MemCopy.class.equals(memCopy2)) {
           System.out.println("MemCopy loaded through loader2 is the same as that loaded by System loader.");
       }
       else {
           System.out.println("MemCopy loaded through loader2 is NOT same as that loaded by System loader.");
       } 

        // call the main method in MemCopy
        System.out.println("Calling MemCopy.main loaded through loader1");
        java.lang.reflect.Method main1 = memCopy1.getMethod("main", 
                                                new Class[] {String[].class});
        main1.invoke(null, new Object[]{ new String[]{} }); 
 
        System.out.println("Calling MemCopy.main loaded through loader2");
        java.lang.reflect.Method main2 = memCopy2.getMethod("main", 
                                                new Class[] {String[].class});
        main2.invoke(null, new Object[]{ new String[]{} });
        
        System.out.println("Calling MemCopy.main loaded through bootstrap loader");
        java.lang.reflect.Method bootMain = bootMemCopy.getMethod("main", 
                                                new Class[] {String[].class});
        bootMain.invoke(null, new Object[]{ new String[]{} }); 
    }
}

