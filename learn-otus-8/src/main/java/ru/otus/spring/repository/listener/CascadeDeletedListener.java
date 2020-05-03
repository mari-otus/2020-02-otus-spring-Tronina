package ru.otus.spring.repository.listener;

import lombok.SneakyThrows;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.spring.repository.annotation.CascadeDel;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Слушатель для каскадного удаления данных.
 *
 * @author Mariya Tronina
 */
public class CascadeDeletedListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoOperations mongoOperations;

    @SneakyThrows
    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Object> event) {
        Reflections reflections = new Reflections("ru.otus.spring");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Document.class);

        for (Class cls : classes) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(CascadeDel.class)
                        && field.isAnnotationPresent(DBRef.class)
                        && field.getType().equals(event.getType())) {
                    mongoOperations.findAllAndRemove(
                            Query.query(Criteria.where(field.getName() + ".$id").is(event.getDocument().get("_id"))),
                            cls);
                }
            }
        }
    }
}
