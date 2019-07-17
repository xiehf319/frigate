package cn.cici.frigate.logistics.service;

import cn.cici.frigate.logistics.pojo.MonitorInfo;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * @description:
 * @createDate:2019/7/1$14:35$
 * @author: Heyfan Xie
 */
@Service
public class MonitorService {

    private static final int CPUTIME = 5000;

    private static final int PERCENT = 100;

    private static final int FAULTLENGTH = 10;

    /**
     * 测试方法.
     *
     * @param args
     * @throws Exception
     * @author amg     * Creation date: 2008-4-30 - 下午04:47:29
     */
    public static void main(String[] args) throws Exception {
        MonitorService service = new MonitorService();
        MonitorInfo monitorInfo = service.getMonitorInfo();
        System.out.println("cpu占有率=" + monitorInfo.getCpuRatio());

        System.out.println("可使用内存=" + monitorInfo.getTotalMemory());
        System.out.println("剩余内存=" + monitorInfo.getFreeMemory());
        System.out.println("最大可使用内存=" + monitorInfo.getMaxMemory());

        System.out.println("操作系统=" + monitorInfo.getOsName());
        System.out.println("总的物理内存=" + monitorInfo.getTotalMemorySize() + "kb");
        System.out.println("剩余的物理内存=" + monitorInfo.getFreePhysicalMemorySize() + "kb");
        System.out.println("已使用的物理内存=" + monitorInfo.getUsedMemory() + "kb");
        System.out.println("线程总数=" + monitorInfo.getTotalThread());
    }


    /** */

    public MonitorInfo getMonitorInfo() {
        final int kb = 1024;
        long totalMemory = Runtime.getRuntime().totalMemory() / kb;
        long freeMemory = Runtime.getRuntime().freeMemory() / kb;
        long maxMemory = Runtime.getRuntime().maxMemory() / kb;

        OperatingSystemMXBean osmxb = ManagementFactory.getOperatingSystemMXBean();
        String osName = System.getProperty("os.name");
        ThreadGroup parentThread;
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent()) {
        }

        int totalThread = parentThread.activeCount();

        double cpuRatio = 0;
        if (osName.toLowerCase().startsWith("windows")) {
            cpuRatio = this.getCpuRatioForWindows();
        }

        MonitorInfo monitorInfo = new MonitorInfo();
        monitorInfo.setTotalMemory(totalMemory);
        monitorInfo.setFreeMemory(freeMemory);
        monitorInfo.setMaxMemory(maxMemory);
        monitorInfo.setOsName(osName);
        monitorInfo.setTotalMemorySize(0L);
        monitorInfo.setFreePhysicalMemorySize(0L);
        monitorInfo.setUsedMemory(0L);
        monitorInfo.setTotalThread(totalThread);
        monitorInfo.setCpuRatio(cpuRatio);
        return monitorInfo;
    }

    /** */

    /**
     * 获得CPU使用率.
     *
     * @return 返回cpu使用率
     * @author amg     * Creation date: 2008-4-25 - 下午06:05:11
     */
    private double getCpuRatioForWindows() {
        try {
            String procCmd = System.getenv("windir")
                    + "//system32//wbem//wmic.exe process get Caption,CommandLine,"
                    + "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
            // 取进程信息
            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
            Thread.sleep(CPUTIME);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
            if (c0 != null && c1 != null) {
                long idletime = c1[0] - c0[0];
                long busytime = c1[1] - c0[1];
                return Double.valueOf(
                        PERCENT * (busytime) / (busytime + idletime))
                        .doubleValue();
            } else {
                return 0.0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0.0;
        }
    }

    /** */

    /**
     * 读取CPU信息.
     *
     * @param proc
     * @return
     * @author amg     * Creation date: 2008-4-25 - 下午06:10:14
     */
    private long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }
            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
                // ThreadCount,UserModeTime,WriteOperation
                String caption = line.substring(capidx, cmdidx - 1)
                        .trim();
                String cmd = line.substring(cmdidx, kmtidx - 1).trim();
                if (cmd.indexOf("wmic.exe") >= 0) {
                    continue;
                }
                // log.info("line="+line);
                if (caption.equals("System Idle Process")
                        || caption.equals("System")) {
                    idletime += Long.valueOf(
                            line.substring(kmtidx, rocidx - 1).trim())
                            .longValue();
                    idletime += Long.valueOf(
                            line.substring(umtidx, wocidx - 1).trim())
                            .longValue();
                    continue;
                }

                kneltime += Long.valueOf(
                        line.substring(kmtidx, rocidx - 1).trim())
                        .longValue();
                usertime += Long.valueOf(
                        line.substring(umtidx, wocidx - 1).trim())
                        .longValue();
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
