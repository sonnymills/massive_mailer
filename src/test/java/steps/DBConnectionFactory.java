package steps;

import org.javalite.activejdbc.Base;

public class DBConnectionFactory {
    public static void prepare() {
        Base.open();
        cleanDB();
    }

    private static void cleanDB() {
        Base.exec("DELETE FROM Template");
        Base.exec("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('Default Template 1', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}')");
        Base.exec("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('RTA Default Template', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}');");
        Base.exec("DELETE FROM sent_mail_visits");
        Base.exec("DELETE FROM sent_mails");
        Base.exec("DELETE FROM events");
        Base.exec("DELETE FROM contact_people");
        Base.exec("DELETE FROM courses");
        Base.exec("DELETE FROM course_contact_notifications");
    }

    public static void close() {
        Base.close();
    }
}
