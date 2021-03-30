
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
    private static ArrayList<String> volumeStringList;//背包容量所在的长字符串数组
    private static String profitUse;//集合中获取到的某项对应用户所选择的的数据
    private static String weightUse;
    private static String volumeUse;//经过处理后的背包容量
    private static String[] splitString_profit;//字符串分割为字符数组
    private static String[] splitString_weight;
    private static String[] splitString_volume;//字符串分割之后的背包容量数组
    private static int[] ints_profit;//字符串数组转换成整型数组
    private static int[] ints_weight;


    public static void main(String[] args) throws IOException {

        int choice = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("*****初始化数据***");
        System.out.println("请输入读入的文件名：");
        String filename = input.next();
        String filePath = "./data/" + filename + ".txt";
        int flag = readTxtFile(filePath);
        if (flag == 1) {
//            System.out.println(volumeStringList);
//            System.out.println(profitList);
//            System.out.println(weightList);
            System.out.println("******读取成功******");
            System.out.println("******数据初始化成功******");
        } else {
            System.out.println("*******读取失败*****");
            System.out.println("*******数据初始化失败*****");
        }
        do {
            choice = doChoice(input);
        } while (choice != 4);
    }

    /**
     * 用户选择
     */
    private static int doChoice(Scanner in) throws IOException {
        System.out.println("\n");
        System.out.println("1.绘制散点图");
        System.out.println("2.排序");
        System.out.println("3.算法实现");
        System.out.println("4.退出");
        System.out.println("请输入选项进行对应操作：");
        int choice = in.nextInt();
        int index;
        switch (choice) {

            case 1:
                System.out.println("想要本文件第几组数据进行生成图表：");
                index = in.nextInt();
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

//                System.out.println("*****************");
//                    for (String s : splitString_profit) {
//                        System.out.println(s);
//                }
                break;
            case 2:
                System.out.println("想要本文件第几组数据进行排序");
                index = in.nextInt();
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
                        System.out.println("输入哪组数据进行动态规划算法");
                        int Dataindex1 = in.nextInt();
                        profitUse = profitList.get(Dataindex1 - 1);
                        weightUse = weightList.get(Dataindex1 - 1);
                        volumeUse = volumeStringList.get(Dataindex1 - 1);
                        // System.out.println(volumeUse+"\n");
                        profitUse.replace(".", "");
                        weightUse.replace(".", "");
                        String[] _splitString_profit1 = convertStrToArray(profitUse);//转存
                        String[] _splitString_weight1 = convertStrToArray(weightUse);//转存
                        String[] _splitString_volume1 = convertStrToArray_volume(volumeUse);
                        int volumeDp = Integer.parseInt(_splitString_volume1[_splitString_volume1.length - 1].replace(".", ""));
                        int[] _ints_profit1 = paseStringToInt(_splitString_profit1);
                        int[] _ints_weight1 = paseStringToInt(_splitString_weight1);
//                        System.out.println("输入容量：");
//                        int volumeDp = in.nextInt();
                        int[][] profitdp = new int[_ints_profit1.length / 3 + 1][3];
                        int[][] weightdp = new int[_ints_profit1.length / 3 + 1][3];
                        int iddp = 0;
                        for (int i = 0; i < 3; i++) {
                            profitdp[0][i] = 0;
                            weightdp[0][i] = 0;
                        }
                        for (int i = 1; i < profitdp.length; i++) {
                            for (int j = 0; j < 3; j++) {
                                profitdp[i][j] = _ints_profit1[iddp];
                                weightdp[i][j] = _ints_weight1[iddp];
                                iddp++;
                            }
                        }
                        long starttimeDp = System.currentTimeMillis();
                        Dp(profitdp, weightdp, volumeDp);
                        long endtimeDp = System.currentTimeMillis();
                        System.out.println("本次动态规划算法执行时间" + String.format("%.6f", Double.valueOf(endtimeDp - starttimeDp) / 1000) + "s");
                        break;
                    case 2:
                        System.out.println("回溯算法");
                        System.out.println("输入哪组数据进行回溯算法");
                        int Dataindex = in.nextInt();
                        profitUse = profitList.get(Dataindex - 1);
                        weightUse = weightList.get(Dataindex - 1);
                        volumeUse = volumeStringList.get(Dataindex - 1);
                        profitUse.replace(".", "");
                        weightUse.replace(".", "");
                        String[] _splitString_profit2 = convertStrToArray(profitUse);//转存
                        String[] _splitString_weight2 = convertStrToArray(weightUse);//转存
                        String[] _splitString_volume2 = convertStrToArray_volume(volumeUse);
                        int[] _ints_profit2 = paseStringToInt(_splitString_profit2);
                        int[] _ints_weight2 = paseStringToInt(_splitString_weight2);
                        int volume = Integer.parseInt(_splitString_volume2[_splitString_volume2.length - 1].replace(".", ""));
//                        System.out.println("输入容量：");
//                        int volume = in.nextInt();
                        int[][] profit = new int[_ints_profit2.length / 3 + 1][3];
                        int[][] weight = new int[_ints_profit2.length / 3 + 1][3];
                        int id = 0;
                        for (int i = 0; i < 3; i++) {
                            profit[0][i] = 0;
                            weight[0][i] = 0;
                        }
                        for (int i = 1; i < profit.length; i++) {
                            for (int j = 0; j < 3; j++) {
                                profit[i][j] = _ints_profit2[id];
                                weight[i][j] = _ints_weight2[id];
                                id++;
                            }
                        }
                        List<Integer> ret = new ArrayList<>();
                        int totalProfit = 0;
                        int totalWeight = 0;
                        //使用Date创建日期对象
                        long start = System.currentTimeMillis();
                        //调用回溯算法
                        recursion(ret, volume, profit, weight, totalProfit, totalWeight, 0, 0);
                        Collections.sort(ret);
                        System.out.println("最优解为：" + ret.get(ret.size() - 1));
                        long over = System.currentTimeMillis();
                        System.out.println("本次回溯算法执行的时间为：" + String.format("%.6f", Double.valueOf((over - start)) / 1000) + " s");

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

    /**
     * 动态规划算法
     */
    private static void Dp(int[][] a, int[][] b, int v) {
        int[][] dp = new int[a.length][v + 1];
        for (int i = 1; i <= a.length - 1; i++) {
            for (int j = 1; j <= v; j++) {
                dp[i][j] = dp[i - 1][j];           // 不选第i组物品
                for (int k = 0; k < 3; k++) { // 第i组物品中选一件
                    if (j >= b[i][k]) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - b[i][k]] + a[i][k]);
                    }
                }
            }
        }
        System.out.println("算法执行完成，最优解为" + dp[a.length - 1][v]);
        // return dp[a.length-1][v];
    }

    /**
     * 回溯算法
     */
    public static void recursion(List<Integer> ret, int volume, int[][] p, int[][] w, int totalProfit, int totalWeight, int i, int j) {
        if (j != 3) {
//            相当于不选当前的项集
            totalProfit = totalProfit + p[i][j];
            totalWeight = totalWeight + w[i][j];
        }
//        如果加上当前物品时总重量超过了背包容量则返回上一级
        if (totalWeight > volume) {
            return;
        }
        if (i == p.length - 1) {
            ret.add(totalProfit);
            return;
        }
        for (int k = 0; k < 4; k++) {
            recursion(ret, volume, p, w, totalProfit, totalWeight, i + 1, k);
        }
    }

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
     * 字符串用" "分割
     */
    private static String[] convertStrToArray_volume(String str) {
        String[] strArray = null;
        strArray = str.split(" "); //拆分字符为" " ,然后把结果交给数组strArray
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
                volumeStringList = new ArrayList<>();
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineTxt.contains("the cubage of knapsack is")) {
                        volumeStringList.add(lineTxt);
                    }
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
