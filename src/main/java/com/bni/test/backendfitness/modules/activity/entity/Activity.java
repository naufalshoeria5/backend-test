package com.bni.test.backendfitness.modules.activity.entity;

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
public class Activity {

    @Id
    private String id;

    @NotNull
    @Size(max = 100)
    private String name;

    @OneToMany(mappedBy = "activity")
    private List<MenuActivity> menuActivity;
}
