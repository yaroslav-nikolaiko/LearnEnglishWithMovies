--
-- Dumping data for table `MediaItem`
--
LOCK TABLES `MediaItem` WRITE;
/*!40000 ALTER TABLE `MediaItem` DISABLE KEYS */;
INSERT INTO `MediaItem` VALUES (4,'TVShow','sample-filename','Two And a Half Men sample',NULL,'19','4',NULL,NULL,1);
/*!40000 ALTER TABLE `MediaItem` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Dumping data for table `WordCell_MediaItem`
--
LOCK TABLES `WordCell_MediaItem` WRITE;
/*!40000 ALTER TABLE `WordCell_MediaItem` DISABLE KEYS */;
INSERT INTO `WordCell_MediaItem` VALUES (4,539),(4,540),(4,541),(4,542),(4,543),(4,544),(4,545),(4,546),(4,547),(4,548),(4,549);
/*!40000 ALTER TABLE `WordCell_MediaItem` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Dumping data for table `WordCell_WORDS`
--
LOCK TABLES `WordCell_WORDS` WRITE;
/*!40000 ALTER TABLE `WordCell_WORDS` DISABLE KEYS */;
INSERT INTO `WordCell_WORDS` VALUES (539,'food'),(540,'batteries'),(541,'hey'),(542,'Thai'),(543,'was'),(544,'Anything'),(545,'bring'),(546,'wine'),(547,'need'),(548,'Hello'),(549,'Julia');
/*!40000 ALTER TABLE `WordCell_WORDS` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Dumping data for table `User`
--
LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'admin@gmail.com','admin','admin');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Dumping data for table `Dictionary`
--
LOCK TABLES `Dictionary` WRITE;
/*!40000 ALTER TABLE `Dictionary` DISABLE KEYS */;
INSERT INTO `Dictionary` VALUES (1,'en','UPPER_INTERMEDIATE','en-ru','ru',1);
/*!40000 ALTER TABLE `Dictionary` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Dumping data for table `WordCell`
--
LOCK TABLES `WordCell` WRITE;
/*!40000 ALTER TABLE `WordCell` DISABLE KEYS */;
INSERT INTO `WordCell` VALUES (539,'KNOWN','food'),(540,'NEW_WORD','batteri'),(541,'NEW_WORD','hey'),(542,'NEW_WORD','thai'),(543,'KNOWN','was'),(544,'KNOWN','anyth'),(545,'KNOWN','bring'),(546,'KNOWN','wine'),(547,'KNOWN','need'),(548,'KNOWN','hello'),(549,'NEW_WORD','julia');
/*!40000 ALTER TABLE `WordCell` ENABLE KEYS */;
UNLOCK TABLES;

-- Dump completed on 2014-12-20 21:36:43
