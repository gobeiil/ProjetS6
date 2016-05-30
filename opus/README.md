OpusProjectModel version 2.1.12 04/05/2016
=====

# Informations générales

Ce projet permet, grâce au script clone-opus-model.sh, de créer un projet Opus fonctionnel du nom de votre choix comprenant des dépendances minimales afin d'exécuter une application GWTP communicant avec une base de données Opus.

# Tutoriel

Voici un bref tutoriel afin de vous aider à comprendre les éléments essentiels d'un projet GWT-GWTP-JPA-Opus.

N.B. : Avant d'entreprendre ce tutoriel, une base de données Opus locale idéalement appelée opus, si vous ne désirez pas modifier le fichier persistence.xml du projet Java, doit être initialisée et populée grâce au script fourni.

##Partie 1

0) Réaliser le tutoriel d'initialisation de la base de données Opus.

1) Téléchargez le fichier clone-opus-model.sh .

2) Ouvrez une invite de commande Unix.
Exécutez la commande 
	cd <chemin où se trouve votre fichier clone-opus-model.sh>
Exécutez la commande 
	./clone-opus-model.sh <nom souhaité pour votre projet>

3-1 Ouvrez IntelliJ IDEA.
Si vous désirez intégrer ce nouveau projet dans le workspace d'un projet déjà ouvert, cliquez sur le menu File > New > Module from Existing Sources ...
Si non, cliquez sur Import Project. Choisissez le dossier de votre projet. Sélectionnez Import project from external model et choisissez Maven. Cliquez sur Next. Spécifiez l'emplacement de votre projet s'il ne l'est pas déjà. Cliquez sur Next 2 fois. Ajouter un Java JDK grâce au plus vert et spécifiez l'emplacement du JDK installé sur l'ordinateur. Cliquez sur Next. Vérifiez que le nom du projet est identique au nom de son dossier. Cliquez sur Finish.
 ou cliquez sur le menu File > New > Project from Existing Sources ...
Sélectionnez le dossier du projet créé grâce au script de clonage, cliquez sur Ok et sur Next 3 fois (assurez-vous qu'il est importé en tant que projet Maven) puis sur Finish.
Le panneau affichable via View > Tool Windows > Maven Projects s'avère très utile lorsque vous désirez déployer un projet ou vérifier la résolution de ses dépendances maven.
Remarquez que deux artifacts sont introuvables par maven : opusCommonsCore et opusCommonsCoreTests. C'est normal, voyons comment corriger la situation.

>Apache Maven est un outil pour la gestion et l'automatisation de production des projets logiciels Java en général et Java EE en particulier. L'objectif recherché est comparable au système Make sous Unix : produire un logiciel à partir de ses sources, en optimisant les tâches réalisées à cette fin et en garantissant le bon ordre de fabrication.
>Il est semblable à l'outil Ant, mais fournit des moyens de configuration plus simples, eux aussi basés sur le format XML. Maven est géré par l'organisation Apache Software Foundation. Précédemment Maven était une branche de l'organisation Jakarta Project.
>Maven utilise un paradigme connu sous le nom de Project Object Model (POM) afin de décrire un projet logiciel, ses dépendances avec des modules externes et l'ordre à suivre pour sa production. Il est livré avec un grand nombre de tâches pré-définies, comme la compilation de code Java ou encore sa modularisation. »
[Source](https://fr.wikipedia.org/wiki/Apache_Maven)

Maven crée un dossier dans votre ordinateur C:/Users/<votre usager>/.m2 (sous Windows). Lorsqu'il rencontre une dépendance dans le pom.xml du projet dans lequel vous travaillez, il commence toujours par vérifier si elle est présente dans le .m2 . Si elle ne l'est pas, il vérifie sa présence dans les répertoires distants indiqués dans ce pom.xml. S'il la trouve, il la télécharge dans le .m2 . S'il ne la trouve pas, il l'indique comme introuvable.
Sachant cela, vous allez simuler que Maven a trouvé les dépendances manquantes. Rendez-vous dans le dossier C:/Users/<votre usager>/.m2/repository/ca/uSherbrooke/gegi/opusCommonsCore/3.9.7.S6info et collez-y les trois fichiers du dossier opusCommonsCore présent dans le opusProjectModel-dependencies.zip. Après avoir cliqué sur «Reimport all Maven projects» dans le haut du panneau Maven projects (cela permet de résoudre la dépendance pour opusCommonsCore, mais crée un dossier vide pour persist-dao dans le .m2), faites de même pour les deux fichiers du dossier persist-dao (destination : C:/Users/<votre usager>/.m2/repository/ca/uSherbrooke/gegi/perist-dao-3.6.4).

3-2 Cliquez sur le menu Run > Edit Configurations.
Si votre projet n'a jamais été exécuté par IntelliJ auparavant, cliquez sur le bouton représentant un + vert en haut à gauche de la fenêtre (Add New Configuration). Dans le menu déroulant, sélectionnez GWT Configuration. S'il n'est pas affiché par défaut dans la liste, forcez l'affichage de tous ses éléments grâce au lien «X items more (irrelevant)».
Inscrivez le nom de votre choix pour l'identifier (habituellement, on inscrit le nom du projet).
Sélectionnez le bon module, votre projet, dans la liste déroulante.
Vous pouvez indiquer à IntelliJ le navigateur dans lequel vous désirez que votre application soit lancée grâce à la liste déroulante «Open in browser».
Cliquez sur Ok.

3-3 Cliquez sur le menu Run > Run ... > <nom de votre projet>
La console affiche que le Super Dev Mode est en cours de lancement.
Des erreurs s'affichent dans la console et après quelques secondes d'attente, un nouvel onglet s'ouvre dans votre navigateur web par défaut et affiche HTTP ERROR: 503.
Allons voir l'erreur qui s'est produite et qui est explicitée dans la console : INFOS: An exception was caught and reported. Message: javax.persistence.PersistenceException: No Persistence provider for EntityManager named <nom de votre projet>.
Terminez l'exécution de votre application en cliquant sur le bouton représentant un carré rouge (stop) dans la console d'exécution.
Pour la régler, double-cliquez sur le nom de votre projet dans le panneau Project et ouvrez les dossiers jusqu'à atteindre src/main/resources.
N.B.: S'il est écrit resources/META-INF au lieu de resources, ajoutez manuellement un fichier bidon dans resources et suite à un clic droit sur votre projet dans le panneau Project Files, cliquez sur Synchronize '<nom de votre projet>'. Vous aurez alors accès au dossier resources. Autrement, c'est le dossier META-INF qui sera étiquetté comme Resources Root et cela ne règlera pas le problème.
Effectuez un clic droit sur le dossier resources et cliquez sur Mark Directory As > Resources Root.

3-4 Cliquez sur le menu Run > Run ... > <nom de votre projet> ou sur le triangle vert (play) dans la console d'exécution.
La console affiche que le Super Dev Mode est en cours de lancement.
Un nouvel onglet s'ouvre dans votre navigateur web par défaut.
Authentifiez-vous au CAS de l'UdeS. Lorsque l'authentification réussit, le navigateur affiche que votre projet est en cours de compilation.
Le projet est compilé et déployé sur un serveur Jetty local.
Hello World s'affiche dans le navigateur.
Fermez l'application grâce au bouton Stop, dont l'icône est un carré rouge, dans l'onglet Dev Mode de la console d'IntelliJ IDEA.

## Partie 2

4) Ouvrez le fichier client HomePagePresenter.java . Une partie du code a volontairement été commentée.
Elle concerne le Gatekeeper. Celui-ci permet de restreindre l'accès d'un usager à un Presenter ou même une méthode. Dans les deux cas, le code diffère. Voir la page web suivante pour plus d'informations : http://dev.arcbees.com/gwtp/core/security/ . Vous pouvez créer autant d'implémentations du Gatekeeper que vous le désirez.
Le cas présenté dans HomePagePresenter est celui qui restreint l'accès à tout le Presenter. Ainsi, un usager qui ne remplit pas les conditions mentionnées dans la fonction canReveal() du AuthenticationGatekeeper ne peut pas accéder au HomePagePresenter ce qui force la page de non autorisation à s'afficher.
Affichez le code du fichier AuthenticationGatekeeper.java . Les conditions choisies pour la fonction canReveal() de cette classe sont les suivantes : la session de l'usager doit être active sur le serveur ET si un ou des identifiants uniques de privilèges sont envoyés au Gatekeeper, l'usager authentifié doit être membre d'un groupe Opus relié à l'application exécutée et au(x) privilège(s) mentionné(s). Voyons voir ce que cela signifie. Poursuivez votre lecture, le tout deviendra plus clair. (N.B. : Vous n'avez pas du tout besoin de connaître le fonctionnement des classes utilisées par ce Gatekeeper.)
Afin qu'un usager ait accès au HomePagePresenter de votre application, une entrée correspondant à votre application doit exister dans la table application de la base de données Opus. Si une telle entrée n'existe pas, vous devez en créer une. De plus, si elle n'existe pas déjà, il est essentiel de créer, dans la table name_token de la base de données Opus, une entrée dont le libellé est identique au history name token du HomePagePresenter, soit "/home", et dont l'application_id est celui de l'entrée précédemment créée dans la table application. Ainsi, votre page d'accueil ainsi que votre application sont liées entre elles dans la base données.
Ensuite, il suffit de créer une entrée dans la table name_token_privilege afin de lier votre name_token au privilège 1 qui devrait être libellé «Accès à l'application».
Enfin, via la table application_privilege_group, vous devez être membre d'un groupe associé à l'identifiant unique de votre application ainsi qu'à l'identifiant unique du privilège souhaité (ici, le privilège ciblé est «Accès à l'application»).
Vous êtes maintenant prêt à décommenter le Gatekeeper du fichier HomePagePresenter, à sauvegarder les modifications de ce fichier et à exécuter l'application.
Voilà ! HelloWorld s'affiche dans le navigateur. Vous savez désormais comment utiliser un Gatekeeper avec GWTP.

5) Modifiez la valeur de la variable donnée en paramètre au AuthenticationGatekeeper de la classe HomePagePresenter pour une autre valeur qui n'existe pas dans la table privilege de la base de données.
Rafraîchissez la page du navigateur où est affichée votre application.
La page de non autorisation s'affiche maintenant, c'est signe que le Gatekeeper fonctionne puisque vous ne possédez pas le privilège requis pour accéder à la page.

N.B. : Aucune manipulation n'est requise pour les étapes suivantes.

6) Dans le fichier <nom de votre projet>.gwt.xml, situé dans le paquet ca.uSherbrooke.gegi.<nom de votre projet>, il est possible d'ajouter certaines configurations et dépendances envers des modules.
Afin de pouvoir utiliser les classes contenues dans le projet opusCommonsCore, il est essentiel que le fichier gwt.xml de votre projet inclue la ligne suivante : <inherits name='ca.uSherbrooke.gegi.commons.core.opusCommonsCore'/>. Elle s'y trouve déjà, vous n'avez pas besoin de l'y ajouter. Cette ligne indique à GWT que le fichier opusCommonsCore.gwt.xml situé dans le paquet spécifié est le point d'entrée d'un projet GWT utilisé par le vôtre.
Un autre outil intéressant fourni par GWTP est le Bootstrapper, à ne pas confondre avec GWTBootstrap3 qui est un module permettant à votre application d'utiliser les widgets (objets de l'interface graphique utilisateur) de Bootstrap3 développé par Twitter. Il permet d'accomplir certaines instructions avant même que l'application soit lancée, ce qui est parfait pour initialiser certains éléments. Pour de plus amples informations à propos du Bootstrapper, suivez ce lien : http://dev.arcbees.com/gwtp/get-started/Bootstrap-Code.html .
Dans la cas du BootstrapperImpl d'opusCommonsCore, il récupère le CIP de l'usager authentifié via le CAS (central authentication service) de l'UdeS et vérifie qu'il correspond bien à un usager Opus, il collecte tous les privilèges reliés aux applications auxquelles l'usager a accès, les stocke dans un singleton en plus de récolter tous les paramètres des applications accessibles par l'usager et de les stocker dans un autre singleton.
Afin que votre application utilise ce bootstrapper, la ligne de code suivante doit se trouver dans le fichier <nom de votre projet>.gwt.xml : <set-configuration-property name="gwtp.bootstrapper" value="ca.uSherbrooke.gegi.commons.core.client.accessRestriction.BootstrapperImpl"/> . Elle s'y trouve déjà, vous n'avez pas besoin de l'y ajouter.

7) Afin de bien utiliser les ActionHandler, il est fortement suggéré de naviguer parmi les tests du projet opusCommonsCore.
Les noms des fonctions de tests sont assez explicites pour vous permettre de vous y retrouver sans trop de peine.
Le contenu des tests de même que le code des classes présentes dans le paquet server/service vous indiquent quels paramètres fournir à une requête.
Il vous suffit ensuite de trouver dans quel ActionHandler la requête de votre choix est utilisée et de vérifier quels sont les paramètres à fournir à l'action pour qu'elle lance la requête de votre choix.

N.B. : le côté client de l'application, ce qui est affiché dans le navigateur, ne transmet JAMAIS l'identifiant unique de l'usager authentifié au serveur. Lorsque vous voyez, dans un test JUnit, que l'identifiant unique de l'usager est envoyé en paramètre à la requête, sachez que les ActionHandler présents dans opusCommonsCore l'envoient automatiquement, ne vous en préoccupez pas.