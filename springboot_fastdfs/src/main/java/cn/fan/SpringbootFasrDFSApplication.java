package cn.fan;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(FdfsClientConfig.class)
public class SpringbootFasrDFSApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFasrDFSApplication.class, args);
    }

}
