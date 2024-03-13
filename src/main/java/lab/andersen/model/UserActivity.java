package lab.andersen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserActivity implements Serializable {

    private int id;
    private int userId;
    private String description;
    private Timestamp dateTime;
}