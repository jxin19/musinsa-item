create table brand
(
    brand_id bigint generated by default as identity,
    name varchar(100) not null,
    primary key (brand_id)
);

alter table if exists brand add constraint uk_brand_name unique (name);

create table item
(
    item_id bigint generated by default as identity,
    category varchar(16),
    retail_price decimal(10,2) not null,
    brand_id bigint,
    primary key (item_id)
);

create index idx_item_brand_category on item (brand_id, category);

alter table if exists item add constraint FKavjjmx4nsbt6246604rwwown0 foreign key (brand_id) references brand;
alter table if exists item add constraint uk_item_category_brand unique (category, brand_id);
