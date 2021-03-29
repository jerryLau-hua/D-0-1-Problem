
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.io.IOException;

public class Main {
    private static ArrayList<String> profitList;//读文件获取到的数据集合
    private static ArrayList<String> weightList;
    private static String profitUse;//集合中获取到的某项对应用户所选择的的数据
    private static String weightUse;
    private static String[] splitString_profit;//字符串分割为字符数组
    private static String[] splitString_weight;
    private static int[] ints_profit;//字符串数组转换成整型数组
    private static int[] ints_weight;


    public static void main(String[] args) throws IOException {
        int choice = 0;
        Scanner input = new Scanner(System.in);
        do {
            choice = doChoice(input);
        } while (choice != 4);
    }

    /**
     * 用户选择
     */
    private static int doChoice(Scanner in) throws IOException {
        System.out.println("****欢迎进入****");
        System.out.println("1.文件读入并绘制散点图");
        System.out.println("2.排序");
        System.out.println("3.算法实现");
        System.out.println("4.退出");
        System.out.println("请输入选项进行对应操作：");
        int choice = in.nextInt();
        switch (choice) {
            case 1:
                System.out.println("输入要读取的文件名");
                String fileName = in.next();
                String filePath = "C:\\Users\\83583\\Desktop\\28\\data_set\\" + fileName + ".txt";
                int flag = readTxtFile(filePath);
                if (flag == 1) {
                    System.out.println("******读取成功******");
                    System.out.println("想要本文件第几组数据进行生成图表：");
                    int index = in.nextInt();
                    //输入选择为第几个数据制表
                    profitUse = profitList.get(index - 1);
                    profitUse = profitUse.replace(".", "");
                    /***价值**/
                    splitString_profit = convertStrToArray(profitUse);//转存
                    ints_profit = paseStringToInt(splitString_profit);//转换为数字
                    /***权重**/
                    weightUse = weightList.get(index - 1);
                    weightUse = weightUse.replace(".", "");
                    splitString_weight = convertStrToArray(weightUse);
                    ints_weight = paseStringToInt(splitString_weight);
                    int drawchartflag = drawchart(ints_profit, ints_weight);
                    if (drawchartflag == 1) {
                        System.out.println("绘制表格成功");
                    } else {
                        System.out.println("绘制表格失败，请确认数据是否正确");
                    }
                } else {
                    System.out.println("*******读取失败*****");
                }
//                System.out.println("*****************");
//                    for (String s : splitString_profit) {
//                        System.out.println(s);
//                }
                break;
            case 2:
                System.out.println("想要本文件第几组数据进行排序");
                int index = in.nextInt();
                profitUse = profitList.get(index - 1);
                weightUse = weightList.get(index - 1);
                profitUse.replace(".", "");
                weightUse.replace(".", "");
                String[] _splitString_profit = convertStrToArray(profitUse);//转存
                String[] _splitString_weight = convertStrToArray(weightUse);//转存
                int[] _ints_profit = paseStringToInt(_splitString_profit);
                int[] _ints_weight = paseStringToInt(_splitString_weight);
                for (int i = 0; i < _ints_profit.length / 3; i++) {
                    for (int j = 0; j < _ints_profit.length / 3 - i - 1; j++) {
                        if ((Double.valueOf(_ints_profit[3 * j + 2]) / Double.valueOf(_ints_weight[3 * j + 2])) <= (Double.valueOf(_ints_profit[3 * (j + 1) + 2]) / Double.valueOf(_ints_weight[3 * (j + 1) + 2]))) {
                            for (int k = 0; k < 3; k++) {
                                swap(_ints_profit, (3 * j + k), (3 * (j + 1) + k));
                                swap(_ints_weight, (3 * j + k), (3 * (j + 1) + k));
                            }
                        }
                    }
                }
                System.out.println("按价值/重量比非递增排序结果如下：");
                System.out.println("排序后的profit：");
                for (int i = 0; i < _ints_profit.length; i++) {
                    System.out.println(_ints_profit[i] + ",");
                }
                System.out.println("排序后的weight");
                for (int i = 0; i < _ints_weight.length; i++) {
                    System.out.println(_ints_weight[i] + ",");
                }
//                System.out.println(_ints_weight);
//                //找出每组数据的第3n个数
//                ArrayList<Integer> Num3_profit = findNum3(_ints_profit, 3);
//                ArrayList<Integer> Num3_weight = findNum3(_ints_weight, 3);
//                //找出每组数据的第3n-1个数
//                ArrayList<Integer> Num2_profit = findNum3(_ints_profit, 2);
//                ArrayList<Integer> Num2_weight = findNum3(_ints_weight, 2);
//                //找出每组数据的第1个数
//                ArrayList<Integer> Num1_profit = findNum3(_ints_profit, 1);
//                ArrayList<Integer> Num1_weight = findNum3(_ints_weight, 1);
//                System.out.println(Num2_profit);
//                System.out.println(Num2_weight);
                break;
            case 3:
                System.out.println("");
                System.out.println("请输入所要执行的算法标号：\n" +
                        "1.动态规划算法" +
                        "2.回溯算法");
                System.out.println("请输入：");
                int option = in.nextInt();
                switch (option) {
                    case 1:
                        System.out.println("动态规划算法");
                        break;
                    case 2:
                        System.out.println("回溯算法");
                        break;
                    default:
                        break;
                }
                break;
            case 4:
                System.out.println("*****已退出***");
                break;
            default:
                System.out.println("请按照提示输入对应选项");
                break;
        }
        return choice;
    }

    /**
     * 交换函数
     */
    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

//    /**
//     * TODO:find atom you want in each item
//     */
//    private static ArrayList<Integer> findNum3(int[] m, int n) {
//        int count = 1;
//        ArrayList<Integer> temp = new ArrayList<>();
//        switch (n) {
//            case 3:
//                for (int i = 2 * count; i < m.length; i = 3 * count - 1) {
//                    count++;
//                    temp.add(m[i]);
//                }
//                return temp;
//            case 2:
//                for (int i =  count; i < m.length; i = 3 * count - 2) {
//                    count++;
//                    temp.add(m[i]);
//                }
//                return temp;
//            case 1:
//                for (int i = 0; i < m.length; i = 3 * count ) {
//                    count++;
//                    temp.add(m[i]);
//                }
//                return temp;
//            default:
//                break;
//        }
//    return null;
//    }

    /**
     * 字符串数组转int数组
     */
    private static int[] paseStringToInt(String[] splitString) {
        int[] ints = new int[splitString.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(splitString[i]);
        }
        return ints;
    }

    /**
     * 字符串分割转存为字符数组
     */
    private static String[] convertStrToArray(String str) {
        String[] strArray = null;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }

    /**
     * 读取数据
     */
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

    /**
     * 绘制图表
     */
    public static int drawchart(int[] profit, int[] weight) throws IOException {

        if (profit.length != 0 && weight.length != 0) {
            //设置散点图数据集
            XYSeries firefox = new XYSeries("weight/profit");
            for (int i = 0; i < profit.length; i++) {
                firefox.add(profit[i], weight[i]);
            }
            //添加到数据集
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(firefox);
            //实现简单的散点图，设置基本的数据
            JFreeChart freeChart = ChartFactory.createScatterPlot(
                    "数据散点图",// 图表标题
                    "weight",//y轴方向数据标签
                    "profit",//x轴方向数据标签
                    dataset,//数据集，即要显示在图表上的数据
                    PlotOrientation.VERTICAL,//设置方向
                    true,//是否显示图例
                    true,//是否显示提示
                    false//是否生成URL连接
            );
            //以面板显示
            ChartPanel chartPanel = new ChartPanel(freeChart);
            chartPanel.setPreferredSize(new java.awt.Dimension(560, 400));
            //创建一个主窗口来显示面板
            JFrame frame = new JFrame("Chart");
            frame.setLocation(500, 400);
            frame.setSize(600, 500);
            //将主窗口的内容面板设置为图表面板
            frame.setContentPane(chartPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            return 1;
        } else {
            return 0;
        }
    }
}
