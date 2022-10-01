package com.codex.ecam.model.app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.codex.ecam.constants.Widgets;
import com.codex.ecam.model.BaseModel;

@Entity
@Table(name = "tbl_app_wiget")
public class AppWiget extends BaseModel {

    private static final long serialVersionUID = 6528284194850085254L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "app_wiget_s")
    @SequenceGenerator(name = "app_wiget_s", sequenceName = "app_wiget_s", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "app_id")
    @ManyToOne(targetEntity = App.class, fetch = FetchType.LAZY)
    private App app;

    @Column(name = "wiget_id")
    private Widgets widgets;


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

	public Widgets getWidgets() {
		return widgets;
	}

	public void setWidgets(Widgets widgets) {
		this.widgets = widgets;
	}



}
