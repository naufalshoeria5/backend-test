package com.bni.test.backendfitness.modules.menuactivity.entity;

import com.bni.test.backendfitness.modules.activity.entity.Activity;
import com.bni.test.backendfitness.modules.menu.enitity.Menu;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuActivity {

    @Id
    private String id;

    @NotNull
    private Long price;

    @Comment("In minutes")
    @NotNull
    private Long duration;

    @NotNull
    private Long numberOfMeeting;

    @ManyToOne(targetEntity = Menu.class)
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;

    @ManyToOne(targetEntity = Activity.class)
    @JoinColumn(name = "activity_id", referencedColumnName = "id")
    private Activity activity;
}
