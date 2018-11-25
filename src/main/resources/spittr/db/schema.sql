drop table if exists spittle;
drop table if exists spitter;

create table spitter (
  id identity,
  username varchar(25) not null,
  password varchar(25) not null,
  firstname varchar(100) not null,
  lastname varchar(100) not null,
  email varchar(50) not null
);

create table spittle (
  id integer identity primary key,
  spitter integer not null,
  message varchar(2000) not null,
  postedTime datetime not null,
  foreign key (spitter) references spitter(id)
);
