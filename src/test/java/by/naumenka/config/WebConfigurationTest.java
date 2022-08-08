package by.naumenka.config;

import by.naumenka.service.xml.TicketXml;
import by.naumenka.service.xml.TicketsListXml;
import by.naumenka.service.xml.XmlToObjectConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@ComponentScan({"by.naumenka.dao.impl", "by.naumenka.storage", "by.naumenka.service"})
@PropertySource("classpath:application.properties")
@Configuration
public class WebConfigurationTest {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(
                TicketXml.class,
                TicketsListXml.class);

        return marshaller;
    }

    @Bean
    public XmlToObjectConverter xmlToObjectConverter() {
        XmlToObjectConverter xmlToObjectConverter = new XmlToObjectConverter();
        xmlToObjectConverter.setMarshaller(jaxb2Marshaller());

        return xmlToObjectConverter;
    }
}