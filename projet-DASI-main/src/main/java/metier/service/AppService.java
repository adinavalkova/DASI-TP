package metier.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import dao.JpaUtil;
import dao.SchoolDAO;
import dao.SupportDAO;
import exceptions.QueryReturnMoreThanOneResult;
import exceptions.QueryReturnZeroResult;
import metier.modele.School;
import metier.modele.Statistic;

public class AppService {

    public List<School> getSchools() {
        JpaUtil.creerContextePersistance();
        SchoolDAO schoolDAO = new SchoolDAO();
        List<School> schools = schoolDAO.getSchools();
        JpaUtil.fermerContextePersistance();
        return schools;
    }

    public Statistic getGlobalStatictic() throws QueryReturnZeroResult, QueryReturnMoreThanOneResult {
        JpaUtil.creerContextePersistance();
        SupportDAO supportDAO = new SupportDAO();

        Statistic statistic = null;

        try {
            int nbSupports = supportDAO.findNumberOfSupports();
            double averageDuration = (double) supportDAO.findAverageDuration();

            statistic = new Statistic();
            statistic.setNbSupports(nbSupports);
            statistic.setAverageDuration(averageDuration);
        }
        catch (NonUniqueResultException e) {
            JpaUtil.fermerContextePersistance();
            throw new QueryReturnMoreThanOneResult("The query returned more than one result, expected only one.");
        }
        catch (NoResultException e) {
            JpaUtil.fermerContextePersistance();
            throw new QueryReturnZeroResult("The query returned no results, expected at least one.");
        }

        JpaUtil.fermerContextePersistance();
        return statistic;
    }
    
}
