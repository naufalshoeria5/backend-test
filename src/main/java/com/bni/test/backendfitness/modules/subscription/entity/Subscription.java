package com.bni.test.backendfitness.modules.subscription.entity;

import com.bni.test.backendfitness.base.baseenum.EStatusSubscription;
import com.bni.test.backendfitness.modules.menu.enitity.Menu;
import com.bni.test.backendfitness.modules.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;

    @Enumerated(EnumType.STRING)
    private EStatusSubscription status;

    private Long total;

    private LocalDate expiredDate;
}
