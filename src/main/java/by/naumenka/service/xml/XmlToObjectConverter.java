package by.naumenka.service.xml;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class XmlToObjectConverter {

    private Jaxb2Marshaller marshaller;
    @Value("${application.tickets}")
    private String FILE_NAME;

    public void setMarshaller(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public List<TicketXml> unmarshallXML() throws IOException {
        log.info("Converting XML to an object graph (unmarshalling)");
        TicketsListXml ticketsListXml;

        try (FileInputStream is = new FileInputStream(FILE_NAME)) {
            ticketsListXml = (TicketsListXml) this.marshaller.unmarshal(new StreamSource(is));
        }
        return ticketsListXml.getTickets();
    }
}