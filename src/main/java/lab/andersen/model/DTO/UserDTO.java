package lab.andersen.model.DTO;

import lab.andersen.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private int age;
    private String surname;
    private String name;

    public UserDTO(User user) {
        id = user.getId();
        age = user.getAge();
        surname = user.getSurname();
        name = user.getName();
    }
}
