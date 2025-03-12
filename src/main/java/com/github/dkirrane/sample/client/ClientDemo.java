//package com.github.dkirrane.sample.client;
//
//import org.springframework.boot.WebApplicationType;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//
//import java.util.concurrent.CountDownLatch;
//
////@SpringBootApplication(
////        scanBasePackages = {"com.github.dkirrane.sample.client"},
////        exclude = {
////                GraphQlAutoConfiguration.class,
////                GraphQlWebMvcAutoConfiguration.class,
////                GraphQlWebFluxAutoConfiguration.class,
////                R2dbcAutoConfiguration.class
////        }
////)
//public class ClientDemo {
//
//    public static void main(String[] args) throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//        new SpringApplicationBuilder(ClientDemo.class)
//                .properties(
//                        "spring.devtools.add-properties=false",
//                        "spring.devtools.livereload.enabled=false"
//                )
//                .web(WebApplicationType.NONE)
//                .run(args);
//        latch.await(); // Wait indefinitely
//    }
//
//}
