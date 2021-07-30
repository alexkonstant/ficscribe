package org.fawks.ficscribe.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @ClassName:Story
 * @Description:
 * @Author : Alexander Konstantinov
 * @Create: 20.07.21 16:12
 */

@Data
@Entity
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String url;

}
