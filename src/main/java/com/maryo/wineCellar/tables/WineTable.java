package com.maryo.wineCellar.tables;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("WINE")
public class WineTable {
    private @Column("ID") @Id Long id;
    private @Column("QUANTITY") Integer quantity;
    private @Column("NAME") String name;
    private @Column("PRICE") Float price;
    private @Column("LOCATION") String location;
}
