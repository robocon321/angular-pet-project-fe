package com.robocon321.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;

@Configuration
public class DateScalarConfig {

//    @Bean
//    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
//        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.Date);
//    }
    
//    @Bean
//    public RuntimeWiringConfigurer dateScalar() {
//    	GraphQLScalarType dateScalar = GraphQLScalarType.newScalar()
//            .name("Date")
//            .description("Java 8 LocalDate as scalar.")
//            .coercing(new Coercing<LocalDate, String>() {
//                @Override
//                public String serialize(final Object dataFetcherResult) {
//                    if (dataFetcherResult instanceof LocalDate) {
//                        return dataFetcherResult.toString();
//                    } else {
//                        throw new CoercingSerializeException("Expected a LocalDate object.");
//                    }
//                }
//
//                @Override
//                public LocalDate parseValue(final Object input) {
//                    try {
//                        if (input instanceof String) {
//                            return LocalDate.parse((String) input);
//                        } else {
//                            throw new CoercingParseValueException("Expected a String");
//                        }
//                    } catch (DateTimeParseException e) {
//                        throw new CoercingParseValueException(String.format("Not a valid date: '%s'.", input), e
//                        );
//                    }
//                }
//
//                @Override
//                public LocalDate parseLiteral(final Object input) {
//                    if (input instanceof StringValue) {
//                        try {
//                            return LocalDate.parse(((StringValue) input).getValue());
//                        } catch (DateTimeParseException e) {
//                            throw new CoercingParseLiteralException(e);
//                        }
//                    } else {
//                        throw new CoercingParseLiteralException("Expected a StringValue.");
//                    }
//                }
//            }).build();
//        return wiringBuilder -> wiringBuilder.scalar(dateScalar);
//    }
}