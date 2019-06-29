/**
 * 单例模式涉及到一个单一的类，该类负责创建自己的对象，同时确保只有单个对象被创建。
 * 这个类提供了一种访问其唯一的对象的方式，可以直接访问，不需要实例化该类的对象。
 *
 * 注意：
 * 1、单例类只能有一个实例。
 * 2、单例类必须自己创建自己的唯一实例。
 * 3、单例类必须给所有其他对象提供这一实例。
 * 优点：
 * 1、在内存里只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例（比如管理学院首页页面缓存）。
 * 2、避免对资源的多重占用（比如写文件操作）。
 * 缺点：
 * 1. 没有接口，不能继承，与单一职责原则冲突，一个类应该只关心内部逻辑，而不关心外面怎么样来实例化。
 *
 * 单例模式VS静态类
 * 1. 单例可以继承和被继承，方法可以被override，而静态方法不可以。
 * 2. 静态方法中产生的对象会在执行后被释放，进而被垃圾回收清理，不会一直存在于内存中。
 * 3. 静态类会在第一次运行时初始化，单例模式可以有其他的选择，即可以延迟加载。
 * 4. 基于2， 3条，由于单例对象往往存在于DAO层（例如sessionFactory），如果反复的
 *    初始化和释放，则会占用很多资源，而使用单例模式将其常驻于内存可以更加节约资源。
 * 5. 静态方法有更高的访问效率。
 * 6. 单例模式很容易被测试。
 *
 * 几个关于静态类的误解：
 *
 * 误解一：静态方法常驻内存而实例方法不是。
 * 实际上，特殊编写的实例方法可以常驻内存，而静态方法需要不断初始化和释放。
 * 误解二：静态方法在堆(heap)上，实例方法在栈(stack)上。
 * 实际上，都是加载到特殊的不可写的代码内存区域中。
 * 静态类和单例模式情景的选择：
 * 情景一：不需要维持任何状态，仅仅用于全局访问，此时更适合使用静态类。
 * 情景二：需要维持一些特定的状态，此时更适合使用单例模式。
 */

// 1. 懒汉式
class singleton {
    /**
     * 不支持多线程。因为没有加锁 synchronized, 不要求线程安全，在多线程不能正常工作。
     * 是否 Lazy 初始化：是
     */
    private static singleton instance;
    //  让构造函数为 private，这样该类就不会被实例化
    private singleton (){}

    //  获取唯一可用的对象
    public static singleton getInstance() {
        if (instance == null) {
            instance = new singleton();
        }
        return instance;
    }

    public void showMessage(){
        System.out.println("This is a singleton instance!");
    }
}

// 2. 懒汉式，线程安全
class singleton {
    /**
     * 是否 Lazy 初始化：是
     * 优点：第一次调用才初始化，避免内存浪费。
     * 缺点：必须加锁 synchronized 才能保证单例，但加锁会影响效率。
     * 以两个线程为例，假设pthread_1刚判断完 intance 为NULL 为真，准备创建实例的时候，切换到了pthread_2, 
     * 此时pthread_2也判断intance为NULL为真，创建了一个实例，再切回pthread_1的时候继续创建一个实例返回，
     * 那么此时就不再满足单例模式的要求了， 因为多线程访问出的问题，加锁使得线程同步；
     */
    private static singleton instance;
    //  让构造函数为 private，这样该类就不会被实例化
    private singleton (){}

    //  获取唯一可用的对象
    public static synchronized singleton getInstance() {
        if (instance == null) {
            instance = new singleton();
        }
        return instance;
    }

    public void showMessage(){
        System.out.println("This is a singleton instance!");
    }
}

// 3. 饿汉式 线程安全
class singleton {
     /**
     * 是否 Lazy 初始化：否
     * 优点：没有加锁，执行效率会提高。
     * 缺点：类加载时就初始化，浪费内存。
     * 饿汉式在类加载的过程中就会创建一个本类的静态对象，所以它的加载过程比懒汉式慢，
     * 但是获得类实例的过程比懒汉式快，并且它在多线程模式下比较安全,因为对象在使用前就已经创建出来了。
     */
    private static singleton instance = new singleton();
    //  让构造函数为 private，这样该类就不会被实例化
    private singleton (){}

    //  获取唯一可用的对象
    public static singleton getInstance() {
        return instance;
    }

    public void showMessage(){
        System.out.println("This is a singleton instance!");
    }
}

public class SingletonPatternDemo {
    public static void main(String[] args) {

        //获取唯一可用的对象
        singleton object = singleton.getInstance();

        //显示消息
        object.showMessage();
    }
}
