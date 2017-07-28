DROP DATABASE IF EXISTS DIET;

CREATE DATABASE DIET;

USE DIET;

#Пока неясная строчка, test возможно пароль. админ типо имени.
#Компилятор говорит есть привелигиозная вариация на эту строку.
#Типо в будущих скул этого не будет или щас не запустица хз
#Пока не делать

#grant all on DIET.* to 'admin'@'localhost' identified by 'test';
#ID пока ещё не существует
CREATE TABLE `Meals`(

  `ID` INT(8) NOT NULL AUTO_INCREMENT,
#LocalDate сменим NOW и вообще DATE
`LocalDateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 # `LocalDateTime` DATE NOT NULL DEFAULT NOW(),
  `Description` VARCHAR(1000) NOT NULL DEFAULT 'no idea which meal is it',
  `Calories` INT(8) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`))


#COLLATE = 'UTF8_GENERAL_CI';
  #Вся ли это строка запускающая автоинкремент, нужен ли двойной автоинкремент или только
  #в классе основе
  ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
  
INSERT INTO Meals(`LocalDateTime`,`Description`,`Calories`) 
VALUES
("2015/5/30/10/0","Завтрак",500),
("2015/5/30/13/0","Обед",1000),
("2015/5/30/20/0","Ужин",500),
("2015/5/31/10/0","Завтрак",1000),
("2015/5/31/13/0","Обед",500),
("2015/5/31/20/0","Ужин",510);

		

 # COLLATE = "UTF_8_GENERAL_CI";








