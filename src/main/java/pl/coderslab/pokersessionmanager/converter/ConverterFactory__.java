//package pl.coderslab.pokersessionmanager.converter;
//
//public class ConverterFactory__ {
//}
//
//5.5.2 ConverterFactory
//        When you need to centralize the conversion logic for an entire class hierarchy, for example, when converting from String to java.lang.Enum objects, implement ConverterFactory:
//
//        package org.springframework.core.convert.converter;
//
//public interface ConverterFactory<S, R> {
//
//    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
//
//}
//    Parameterize S to be the type you are converting from and R to be the base type defining the range of classes you can convert to. Then implement getConverter(Class<T>), where T is a subclass of R.
//
//        Consider the StringToEnum ConverterFactory as an example:
//
//        package org.springframework.core.convert.support;
//
//final class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {
//
//    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
//        return new StringToEnumConverter(targetType);
//    }
//
//    private final class StringToEnumConverter<T extends Enum> implements Converter<String, T> {
//
//        private Class<T> enumType;
//
//        public StringToEnumConverter(Class<T> enumType) {
//            this.enumType = enumType;
//        }
//
//        public T convert(String source) {
//            return (T) Enum.valueOf(this.enumType, source.trim());
//        }
//    }
//}
