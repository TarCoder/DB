package com.db.test.config;

import java.io.IOException;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class MyTypeFilter implements TypeFilter {

	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException {
		metadataReader.getAnnotationMetadata();//获取当前正在扫描的类的注解信息
		ClassMetadata classMetadata = metadataReader.getClassMetadata();//获取当前正在扫描的类的信息
		metadataReader.getResource();//获取当前正在扫描的类的资源信息（路径...）
		String name = classMetadata.getClassName();
		System.out.println(name);
		return name.endsWith("Service");
	}

}
