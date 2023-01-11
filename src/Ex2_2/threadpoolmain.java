package Ex2_2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class threadpoolmain {

    public static void main(String[] args) throws InterruptedException {
        CustomExecutor CE = new CustomExecutor();
        Task<Integer> task = Task.createTask(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("starting COMPUTATIONAL");
                Thread.sleep(10*1000);
                System.out.println("ending COMPUTATIONAL");
                return 5*5;
            }
        }, TaskType.COMPUTATIONAL);


        Task<String> task2 = Task.createTask(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("starting IO");
                Thread.sleep(7*1000);
                System.out.println("ending IO");
                return "IO";
            }
        }, TaskType.IO);

        Task task3 = Task.createTask(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                System.out.println("starting OTHER");
                Thread.sleep(100);
                System.out.println("ending OTHER");
                return "OTHER";
            }
        }, TaskType.OTHER);

        CE.submit(task2);
        CE.submit(task3);
        CE.submit(task);
        CE.submit(Task.createTask(()->"COMPUTATIONAL2", TaskType.COMPUTATIONAL));
        CE.submit(Task.createTask(()->"IO2", TaskType.IO));
        CE.submit(Task.createTask(()->"OTHER2", TaskType.OTHER));
        CE.submit(Task.createTask(()->"IO3", TaskType.IO));
        CE.submit(Task.createTask(()->"COMPUTATIONAL3", TaskType.COMPUTATIONAL));
        CE.submit(Task.createTask(()->"OTHER3", TaskType.OTHER));



//        while(true){
//            int time=0;
//            ArrayList<Future> f = new ArrayList<>();
//            f.add(CE.submit(task2));
//            f.add(CE.submit(task3));
//            f.add(CE.submit(task));
//            try {
//                task2.get();
//                Thread.sleep(500);
//                task.get();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            while(time<50) {
//                System.out.println(task.isDone()+","+task2.isDone()+","+task3.isDone());
//                Thread.sleep(1*1000);
//                time++;
//            }

//            for(Future i : f){
//                try {
//                    i.get();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
        //}
//            System.out.println(task.isDone()+","+task2.isDone()+","+task3.isDone());
//            Thread.sleep(500);

        // }
//
        try {
            CE.executeAllTasks();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //CE.submit(()->5*10,TaskType.COMPUTATIONAL);



        //    CE.submit(task3);
//
//    CE.submit(task);
//
//    CE.submit(task2);

//        ArrayList<Future> f =new ArrayList<>();
//        f.add( CE.submit(task3));
//        System.out.println(CE.getQueue().toString());
//        f.add( CE.submit(task));
//        System.out.println(CE.getQueue().toString());
//        f.add(CE.submit(task2));
//        System.out.println(CE.getQueue().toString());
//        f.add(CE.submit(task2));
//        for (Future i:f)
//        {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(i.isDone());
//            try {
//                try {
//                    System.out.println(i.get());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(i.isDone());
//
//            System.out.println(CE.getQueue());
//        }
//
//        CE.gracefullyTerminate();
//
//
//
//
//        System.out.println(CE.getQueue());
    }
}

