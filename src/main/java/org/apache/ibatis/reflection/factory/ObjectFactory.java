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
package org.apache.ibatis.reflection.factory;

import java.util.List;
import java.util.Properties;

/**
 * MyBatis 每次创建结果对象的新实例时，都会使用一个对象工厂（ObjectFactory）实例来完成。
 * 默认的对象工厂DefaultObjectFactory仅仅是实例化目标类，
 * 要么通过默认构造方法，要么在参数映射存在的时候通过参数构造方法来实例化。
 * 如果想覆盖对象工厂的默认行为比如给某些属性设置默认值(有些时候直接修改对象不可行，
 * 或者由于不是自己拥有的代码或者改动太大)，则可以通过创建自己的对象工厂来实现
 *
 *
 * 从这个接口定义可以看出，它包含了两种通过反射机制构造实体类对象的方法，
 * 一种是通过无参构造函数，一种是通过带参数的构造函数。
 * 同时，为了使工厂类能设置其他属性，还提供了setProperties()方法。
 *
 * MyBatis uses an ObjectFactory to create all needed new Objects.
 *
 * @author Clinton Begin
 */
public interface ObjectFactory {

  /**
   * Sets configuration properties.
   * @param properties configuration properties
   */
  default void setProperties(Properties properties) {
    // NOP
  }

  /**
   * Creates a new object with default constructor.
   * @param type Object type
   * @return
   */
  <T> T create(Class<T> type);

  /**
   * Creates a new object with the specified constructor and params.
   * @param type Object type
   * @param constructorArgTypes Constructor argument types
   * @param constructorArgs Constructor argument values
   * @return
   */
  <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs);

  /**
   * Returns true if this object can have a set of other objects.
   * It's main purpose is to support non-java.util.Collection objects like Scala collections.
   *
   * @param type Object type
   * @return whether it is a collection or not
   * @since 3.1.0
   */
  <T> boolean isCollection(Class<T> type);

}
