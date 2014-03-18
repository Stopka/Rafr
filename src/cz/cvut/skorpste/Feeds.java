package cz.cvut.skorpste;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by stopka on 14.3.14.
 */
public class Feeds {
    long nextId = 0;
    HashMap<Long, Feed> feeds = new HashMap<Long, Feed>();

    public static Feeds singleton=null;

    public static Feeds get(){
        if(singleton==null){
            singleton=new Feeds();
        }
        return singleton;
    }

    public Feeds() {
        String article = "Stejně tak jako existovala ListActivity, existuje i ListFragment (a přestože odkazuji do dokumentace na android.app.ListFragment, vy děďte od android.support.v4.app.ListFragment (obecně, kdykoli dostanete při importu na výběr z více tříd stejného jména, vyberte tu ze Support Library, pokud nemáte dobrý důvod udělat to jinak)). Tato třída nabízí některá zjednodušení pro práci s ListView. Nabízí i vestavěný layout, ale my tentokrát chceme, aby se uživateli, když zatím nemá uložené žádné poznámky, zobrazil text \"Nemáte uloženy žádné poznámky.\", a to červeně, takže si vytvoříme vlastní layout. Na ten jsou kladeny jisté podmínky, a to, že ListView musí mít id @android:id/list a TextView, které se zobrazí, kdyby mělo ListView být prázdné, musí mít id  @android:id/empty";
        String author = "Jan Novák";
        addFeed("Tomáš Halík získal jako první Čech Templetonovu cenu", article, author, new Date());
        addFeed("Renziho recept na oživení Itálie: menší daně pro slabší i podniky", article, author, new Date());
        addFeed("Google Disk několikanásobně snižuje ceny, 1 TB za 200 korun měsíčně", article, author, new Date());
        addFeed("Microsoft na jaře nabídne Office 365 Personal pro jednotlivce", article, author, new Date());
        addFeed("Slováci se konečně dočkali, dostanou Google Music", article, author, new Date());
        addFeed("Intel: Můžeme vyrobit rychlejší Thunderbolt, ale bylo by to zbytečné", article, author, new Date());
        addFeed("Před 25 lety vytvořil jednoduchý katalog, dnes je z něj celosvětový fenomén", article, author, new Date());
        addFeed("Plzeňsko pokryje rychlý internet LTE", article, author, new Date());
        addFeed("Nejlepší PC od Applu vypadá jako odpadkový koš. Stojí 250 000 Kč", article, author, new Date());
        addFeed("Operátoři budou muset vracet část pokut za předčasně ukončené smlouvy", article, author, new Date());
    }

    public void addFeed(String title, String article, String author, Date date) {
        setFeed(new Feed(nextId, title, article, author, date));
        nextId++;
    }

    public void setFeed(Feed feed) {
        feeds.put(feed.getID(), feed);
        if (feed.getID() >= nextId) {
            nextId = feed.getID() + 1;
        }
    }

    public Feed getFeed(long id) {
        return feeds.get(id);
    }

    public Feed[] getFeeds() {
        Collection<Feed> c=feeds.values();
        Feed[] result=new Feed[c.size()];
        int index=0;
        for(Feed f:c){
            result[index]=f;
            index++;
        }
        return result;
    }


}
