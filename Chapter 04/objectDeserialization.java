import java.io.*;
import java.util.Objects;

public class SecureDeserialization {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Serialize an object to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(new Car("ABC", "DEF", "Red"));
        }

        byte[] serializedData = baos.toByteArray();

        // Create a filter that only allows deserialization of the 'Car' class
        ObjectInputFilter filter = info -> {
            if (Objects.equals(info.serialClass(), Car.class)) {
                return ObjectInputFilter.Status.ALLOWED;
            }
            return ObjectInputFilter.Status.REJECTED;
        };

        // Deserialize with the filter in place
        ByteArrayInputStream bais = new ByteArrayInputStream(serializedData);
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            ois.setInputFilter(filter);
            Car deserializedCar = (Car) ois.readObject();
            System.out.println(deserializedCar);
        }
    }
}
