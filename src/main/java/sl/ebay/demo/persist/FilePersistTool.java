package sl.ebay.demo.persist;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import sl.ebay.demo.common.Constants;
import sl.ebay.demo.pojo.AccessResource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class FilePersistTool implements ApplicationListener<ContextClosedEvent>, ApplicationContextAware {

    private static FileInputStream fileInputStream;
    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;

    private static FileOutputStream fileOutputStream;
    private static OutputStreamWriter outputStreamWriter;
    private static BufferedWriter bufferedWriter;

    private ApplicationContext applicationContext;

    private static Set<AccessResource> USER_RESOURCES_MAPPING_CACHE = new HashSet<>();

    static {
        String workerDirectoryPath = System.getProperty(Constants.PERSIST_DIRECTORY_PATH_SYSTEM_PROPERTY_KEY);
        File f = new File(workerDirectoryPath + File.separator + Constants.PERSIST_DIRECTORY_FILENAME);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fileInputStream = new FileInputStream(f);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            fileOutputStream = new FileOutputStream(f, true);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                String[] split = readLine.split(Constants.SPLIT_SIGN);
                USER_RESOURCES_MAPPING_CACHE.add(
                        new AccessResource(split[0] == null ? -1 : Long.parseLong(split[0]), split[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            closeStream();
        } catch (IOException e) {
            e.printStackTrace();
            closeStream();
        }
    }
    // 单机环境下同步， 分布式环境下需要分布式锁，同步写单存储资源
    // 互斥锁若不对读同步则存在写入读延迟
    public synchronized void write(List<AccessResource> accessResourceList) throws IOException {
        if (accessResourceList != null && !accessResourceList.isEmpty()) {
            for (AccessResource accessResource : accessResourceList) {
                // 去重 防止恶意打爆磁盘
                if (!USER_RESOURCES_MAPPING_CACHE.contains(accessResource)) {
                    bufferedWriter.write(accessResource.getUserId()
                            + Constants.SPLIT_SIGN + accessResource.getResourceName());
                    bufferedWriter.write(System.getProperty(Constants.PERSIST_FILE_LINE_SEPARATOR_SYSTEM_PROPERTY_KEY));
                    bufferedWriter.flush();
                    // 更新缓存
                    USER_RESOURCES_MAPPING_CACHE.add(accessResource);
                }
            }

        }
    }

    // 系统仅一个写入点一个读取点，加synchronized则不存在写入读延迟
    public synchronized Set<AccessResource> read() {
        return USER_RESOURCES_MAPPING_CACHE;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * close file stream before spring closed
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (event.getApplicationContext().equals(applicationContext)) {
            closeStream();
        }
    }

    private static void closeStream() {
        for (Closeable closeable : new Closeable[]{
                bufferedWriter, outputStreamWriter,
                fileOutputStream, bufferedReader,
                inputStreamReader, fileInputStream
        }) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
