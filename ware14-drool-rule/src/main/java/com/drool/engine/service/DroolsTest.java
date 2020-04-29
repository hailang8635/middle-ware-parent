package com.drool.engine.service;

import com.alibaba.fastjson.JSONObject;
import com.drool.engine.entity.ItemCity;
import com.drool.engine.entity.Person;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.math.BigDecimal;

public class DroolsTest {
    static KieContainer container = null;

    private final static KieServices kieServices = KieServices.Factory.get();
    private static final String RULES_PATH = "droolRule/";


    public static final void main(String[] args) {
//        test1();
        test2();
        test3();
    }

    public static void test2() {
        KieSession kieSession = kieSession();

        Person person = new Person("Kevin", 12);
        kieSession.insert(person);
        kieSession.fireAllRules();

        Person person2 = new Person("Jack", 51);
        kieSession.insert(person2);
        kieSession.fireAllRules();

        Person person3 = new Person("Jack", 35);
        kieSession.insert(person3);
        kieSession.fireAllRules();

        System.out.println(JSONObject.toJSONString(person));
        System.out.println(JSONObject.toJSONString(person2));
        System.out.println(JSONObject.toJSONString(person3));
        kieSession.dispose();
    }

    public static void test3() {
        KieSession kieSession = kieSession();
        ItemCity item1 = new ItemCity(ItemCity.City.PUNE, ItemCity.Type.MEDICINES, new BigDecimal(100));
        kieSession.insert(item1);

        ItemCity item2 = new ItemCity(ItemCity.City.PUNE, ItemCity.Type.GROCERIES, new BigDecimal(100));
        kieSession.insert(item2);

        ItemCity item3 = new ItemCity(ItemCity.City.NAGPUR, ItemCity.Type.MEDICINES, new BigDecimal(100));
        kieSession.insert(item3);

        ItemCity item4 = new ItemCity(ItemCity.City.NAGPUR, ItemCity.Type.GROCERIES, new BigDecimal(100));
        kieSession.insert(item4);

        ItemCity item5 = new ItemCity(ItemCity.City.NAGPUR, ItemCity.Type.WATCHES, new BigDecimal(100));
        kieSession.insert(item5);
        ItemCity item6 = new ItemCity(ItemCity.City.NAGPUR, ItemCity.Type.LUXURYGOODS, new BigDecimal(100));
        kieSession.insert(item6);

        kieSession.fireAllRules();
        System.out.println(JSONObject.toJSONString(item1));
        System.out.println(JSONObject.toJSONString(item2));
        System.out.println(JSONObject.toJSONString(item3));
        System.out.println(JSONObject.toJSONString(item4));
        System.out.println(JSONObject.toJSONString(item5));
        System.out.println(JSONObject.toJSONString(item6));




    }

    public static KieFileSystem kieFileSystem() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] files = new Resource[0];
        try {
            files = resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "*.*");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = null;
        for (Resource file : files) {
            path = RULES_PATH + file.getFilename();
            System.out.println(path);
            kieFileSystem.write(ResourceFactory.newClassPathResource(path, "UTF-8"));
        }
        return kieFileSystem;
    }

    public static KieContainer kieContainer() {
        KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();
        return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
    }
    public static KieSession kieSession() {
        return kieContainer().newKieSession();
    }
}
