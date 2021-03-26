import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<String> profitList;
    private static ArrayList<String> weightList;

    public static void main(String[] args) {
        System.out.println("****欢迎****\n请选择：");
        System.out.println("1.文件读入并绘制散点图");
        System.out.println("2.排序");
        System.out.println("3.算法实现");
        System.out.println("4.退出");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                System.out.println("输入要读取的文件名");
                String fileName = in.next();
                String filePath = "C:\\Users\\83583\\Desktop\\28\\data_set\\" + fileName + ".txt";
                int flag=readTxtFile(filePath);
                if(flag==1){
                    System.out.println("******读取成功******");
                    System.out.println("想要第几组数据进行生成图表：");
                    int index = in.nextInt();
                    String profitUse = profitList.get(index - 1);
//                    System.out.println(profitUse);

                    String weightUse = weightList.get(index - 1);
//                    System.out.println(weightUse);
                }else {
                    System.out.println("*******读取失败*****");
                }

                break;
            case 2:


                break;
            case 3:

                break;
            case 4:
                break;
            default:
                System.out.println("请按照提示输入对应选项");
                break;
        }
    }

    public static int readTxtFile(String filePath) {
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                profitList = new ArrayList<>();
                weightList = new ArrayList<>();
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineTxt.equals("The profit of items are:")) {
                        String profit = bufferedReader.readLine();
                        profitList.add(profit);
                    }
                    if (lineTxt.equals("The weight of items are:")) {
                        String weight = bufferedReader.readLine();
                        weightList.add(weight);
                    }
                }
                read.close();
                return 1;
            } else {
                System.out.println("找不到指定的文件");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return 1;
    }
}
