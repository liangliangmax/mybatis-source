/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.session;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

/**
 * Builds {@link SqlSession} instances.
 *
 * SqlSessionFactory提供了根据字节流、字符流以及直接使用org.apache.ibatis.session.Configuration配置类（后续我们会详细讲到）三种途径的读取配置信息方式，
 * 无论是字符流还是字节流方式，
 * 首先都是将XML配置文件构建为Configuration配置类，
 * 然后将Configuration设置到SqlSessionFactory默认实现DefaultSqlSessionFactory的configurationz字段并返回。
 * 所以，它本身很简单，解析配置文件的关键逻辑都委托给XMLConfigBuilder了，
 * 我们以字符流也就是java.io.Reader为例进行分析，SqlSessionFactoryBuilder使用了XMLConfigBuilder作为解析器。
 */
public class SqlSessionFactoryBuilder {

  // 使用java.io.Reader构建
  public SqlSessionFactory build(Reader reader) {
    return build(reader, null, null);
  }

  public SqlSessionFactory build(Reader reader, String environment) {
    return build(reader, environment, null);
  }

  public SqlSessionFactory build(Reader reader, Properties properties) {
    return build(reader, null, properties);
  }

  public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
    try {
      XMLConfigBuilder parser = new XMLConfigBuilder(reader, environment, properties);
      return build(parser.parse());
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error building SqlSession.", e);
    } finally {
      ErrorContext.instance().reset();
      try {
        reader.close();
      } catch (IOException e) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }

  public SqlSessionFactory build(InputStream inputStream) {
    return build(inputStream, null, null);
  }

  public SqlSessionFactory build(InputStream inputStream, String environment) {
    return build(inputStream, environment, null);
  }

  public SqlSessionFactory build(InputStream inputStream, Properties properties) {
    return build(inputStream, null, properties);
  }

  public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
    try {
      XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
      return build(parser.parse());
    } catch (Exception e) {
      throw ExceptionFactory.wrapException("Error building SqlSession.", e);
    } finally {
      ErrorContext.instance().reset();
      try {
        inputStream.close();
      } catch (IOException e) {
        // Intentionally ignore. Prefer previous error.
      }
    }
  }

  public SqlSessionFactory build(Configuration config) {
    return new DefaultSqlSessionFactory(config);
  }

}
