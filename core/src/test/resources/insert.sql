SET REFERENTIAL_INTEGRITY FALSE;
INSERT INTO MediaItem VALUES (4,'TVShow','sample-filename','Two And a Half Men sample',NULL,'19','4',NULL,NULL,1);

INSERT INTO WordCell_MediaItem VALUES (4,539),(4,540),(4,541),(4,542),(4,543),(4,544),(4,545),(4,546),(4,547),(4,548),(4,549);

INSERT INTO WordCell_WORDS VALUES (539,'food'),(540,'batteries'),(541,'hey'),(542,'Thai'),(543,'was'),(544,'Anything'),(545,'bring'),(546,'wine'),(547,'need'),(548,'Hello'),(549,'Julia');

INSERT INTO User VALUES (1,'admin@gmail.com','admin','admin');

INSERT INTO Dictionary VALUES (1,'en','UPPER_INTERMEDIATE','en-ru','ru',1);

INSERT INTO WordCell VALUES (539,'KNOWN','food'),(540,'NEW_WORD','batteri'),(541,'NEW_WORD','hey'),(542,'NEW_WORD','thai'),(543,'KNOWN','was'),(544,'KNOWN','anyth'),(545,'KNOWN','bring'),(546,'KNOWN','wine'),(547,'KNOWN','need'),(548,'KNOWN','hello'),(549,'NEW_WORD','julia');

SET REFERENTIAL_INTEGRITY TRUE;