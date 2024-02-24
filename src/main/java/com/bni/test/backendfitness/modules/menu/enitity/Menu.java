package com.bni.test.backendfitness.modules.menu.enitity;

import com.bni.test.backendfitness.modules.menuactivity.entity.MenuActivity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    private String id;

    @NotNull
    @Size(max = 100)
    private String name;

    private String description;

    private Integer numberOfMeeting;

    private Long total;

    @OneToMany(mappedBy = "menu")
    private List<MenuActivity> menuActivity;
}
