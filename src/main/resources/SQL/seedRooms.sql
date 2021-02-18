insert into rooms (name, id)
values ('Slaapkamer', 1);

insert into rooms (name, id)
values ('Keuken', 2);

insert into rooms (name, id)
values ('Woonkamer', 3);

insert into lights (name, id)
values ('Rail keukentafel', 1);

insert into lights (name, id)
values ('Led aanrecht', 2);

insert into lights (name, id)
values ('Spot kookeiland', 3);

update lights set rooms_id = 2 where id = 1;

update lights set rooms_id = 2 where id =  2;

update lights set rooms_id = 2 where id =  3;

insert into lightgroups (name, id)
values ('Avond sfeer', 1);

insert into lightgroups (name, id)
values ('Ochtend weekdagen', 2);

update lights set lightgroups_id = 2 where id = 1;