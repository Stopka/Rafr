package cz.cvut.skorpste.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by stopka on 14.3.14.
 */
public class Articles {
    long nextId = 0;
    HashMap<Long, Article> feeds = new HashMap<Long, Article>();

    public static Articles singleton=null;

    public static Articles get(){
        if(singleton==null){
            singleton=new Articles();
        }
        return singleton;
    }

    public Articles() {
        String article = "Stejně tak jako existovala ListActivity, existuje i ArticleListFragment (a přestože odkazuji do dokumentace na android.app.ArticleListFragment, vy děďte od android.support.v4.app.ArticleListFragment (obecně, kdykoli dostanete při importu na výběr z více tříd stejného jména, vyberte tu ze Support Library, pokud nemáte dobrý důvod udělat to jinak)). Tato třída nabízí některá zjednodušení pro práci s ListView. Nabízí i vestavěný layout, ale my tentokrát chceme, aby se uživateli, když zatím nemá uložené žádné poznámky, zobrazil text \"Nemáte uloženy žádné poznámky.\", a to červeně, takže si vytvoříme vlastní layout. Na ten jsou kladeny jisté podmínky, a to, že ListView musí mít id @android:id/list a TextView, které se zobrazí, kdyby mělo ListView být prázdné, musí mít id  @android:id/empty";
        String author = "Jan Novák";
        addArticle("Tomáš Halík získal jako první Čech Templetonovu cenu", article, author, new Date());
        addArticle("Renziho recept na oživení Itálie: menší daně pro slabší i podniky", article, author, new Date());
        addArticle("Google Disk několikanásobně snižuje ceny, 1 TB za 200 korun měsíčně", article, author, new Date());
        addArticle("Microsoft na jaře nabídne Office 365 Personal pro jednotlivce", article, author, new Date());
        addArticle("Slováci se konečně dočkali, dostanou Google Music", article, author, new Date());
        addArticle("Intel: Můžeme vyrobit rychlejší Thunderbolt, ale bylo by to zbytečné", article, author, new Date());
        addArticle("Před 25 lety vytvořil jednoduchý katalog, dnes je z něj celosvětový fenomén", article, author, new Date());
        addArticle("Plzeňsko pokryje rychlý internet LTE", article, author, new Date());
        addArticle("Nejlepší PC od Applu vypadá jako odpadkový koš. Stojí 250 000 Kč", article, author, new Date());
        addArticle("Operátoři budou muset vracet část pokut za předčasně ukončené smlouvy", article, author, new Date());
    }

    public void addArticle(String title, String article, String author, Date date) {
        setArticle(new Article(nextId, title, article, author, date));
        nextId++;
    }

    public void setArticle(Article article) {
        feeds.put(article.getID(), article);
        if (article.getID() >= nextId) {
            nextId = article.getID() + 1;
        }
    }

    public Article getFeed(long id) {
        return feeds.get(id);
    }

    public Article[] getFeeds() {
        Collection<Article> c=feeds.values();
        Article[] result=new Article[c.size()];
        int index=0;
        for(Article f:c){
            result[index]=f;
            index++;
        }
        return result;
    }


}
