package com.neolith.focus.model.app;

import com.neolith.focus.model.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "tbl_related_app")
public class RelatedApp extends BaseModel {

    private static final long serialVersionUID = 1640683492532151771L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "related_app_s")
    @SequenceGenerator(name = "related_app_s", sequenceName = "related_app_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "app_id")
    @ManyToOne(targetEntity = App.class, fetch = FetchType.LAZY)
    private App app;

    @JoinColumn(name = "related_app_id")
    @ManyToOne(targetEntity = App.class, fetch = FetchType.LAZY)
    private App relatedApp;

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

    public App getRelatedApp() {
        return relatedApp;
    }

    public void setRelatedApp(App relatedApp) {
        this.relatedApp = relatedApp;
    }

}
