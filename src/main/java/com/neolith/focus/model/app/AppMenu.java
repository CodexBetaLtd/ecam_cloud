package com.neolith.focus.model.app;

import com.neolith.focus.constants.Menu;
import com.neolith.focus.model.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "tbl_app_menu")
public class AppMenu extends BaseModel {

    private static final long serialVersionUID = 6528284194850085254L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "app_menu_s")
    @SequenceGenerator(name = "app_menu_s", sequenceName = "app_menu_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "app_id")
    @ManyToOne(targetEntity = App.class, fetch = FetchType.LAZY)
    private App app;

    @Column(name = "menu_id")
    private Menu menu;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

}
