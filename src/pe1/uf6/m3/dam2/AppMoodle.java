package pe1.uf6.m3.dam2;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Vector;

public class AppMoodle {
    private static final String DATABASE = "$objectdb/db/moodle.odb";
    private static final int MIDA_PAGINA = 10;
    private static final int COL_RESULTAT = 15;

    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void main(String[] args) throws Exception {
        initEM();

        // Mètodes Exercici 3. Operacions CRUD
        gestionarDades1();
        gestionarDades2();
        gestionarDades3();
        gestionarDades4();
        gestionarDades5();

        // Mètodes Exercici 4. Consultes
        //consultaDades1(2, "2018-09-01", "o");
        //consultaDades2(5, 2, "DAM2");
        //consultaDades3(1, "ASIX");

        closeDatabase();

        System.out.println("FINAL");
    }

    public static void gestionarDades1() {
        /*
            1. Afegeix un nou estudiant amb les següents dades:
               id: 2201, username: nul, email: "student2201@gmail.com",
               firstname: "Anna", lastname: "Garcia", city: "Sant Boi", lastlogin: null
        */

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Student student = new Student(2201, null, "Anna", "Garcia", "student2201@gmail.com", "Sant Boi", null);
            em.persist(student);

            tx.commit();
        } catch (Exception e) {
            System.err.println("Good 1");
            System.err.println(e);
            //throw new RuntimeException(e);
        } finally {
            if (tx.isActive()) tx.rollback();
        }
    }

    public static void gestionarDades2() {
        /*
            1. Consultar el curs amb «id» valor 2 fent servir la consulta amb nom «Course.findById».
            2. Canvia la categoria a "DAM2/DAW2"
            3. Afegeix un nou estudiant al curs amb les següents dades
               id: 2202, username: "student2202", email: "student2202@gmail.com",
               firstname: "Joan", lastname: "Planas", city: "Sant Boi", lastlogin: null
            4. Posa una qualificació de 7.5 a l'alumne anterior
         */

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            TypedQuery<Course> query = em.createNamedQuery("Course.findById", Course.class);
            query.setParameter("id", 2).getSingleResult();

            Course course = query.getSingleResult();
            course.setCategory("DAM2/DAW2");

            Student student = new Student(2202, "student2202", "Joan", "Planas", "student2202@gmail.com", "Sant Boi", null);
            course.addStudent(student);

            course.gradeStudent(student, 7.5);

            em.persist(course);

            tx.commit();
        } catch (Exception e) {
            System.err.println("BAD 2");

            throw new RuntimeException(e);

            //System.err.println(e);
            //return;
        } finally {
            if (tx.isActive()) tx.rollback();
        }

        System.err.println("Good 2");
    }

    public static void gestionarDades3() {
        /*
            1. Consultar les inscripcions a cursos (Enrollment) de l'estudiant amb «id» valor 63
            2. Esborrar l'estudiant dels cursos on encara no tingui qualificació
         */

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            TypedQuery<Student> studentQuery = em.createNamedQuery("Student.findById", Student.class);
            studentQuery.setParameter("id", 63);

            Student student = studentQuery.getSingleResult();

            TypedQuery<Enrollment> enrollmentQuery = em.createNamedQuery("Enrollment.findByStudent", Enrollment.class);
            enrollmentQuery.setParameter("student", student);

            for (Enrollment e : enrollmentQuery.getResultList()) {
                if (e.getGrade() == null) {
                    em.remove(e);
                }
            }

            tx.commit();
        } catch (Exception e) {
            System.err.println("BAD 3");
            System.err.println(e);
            //throw new RuntimeException(e);
        } finally {
            if (tx.isActive()) tx.rollback();
        }

        System.err.println("Good 3");
    }

    public static void gestionarDades4() {
        /*
            1. Consultar el curs amb «id» valor 314 fent servir la consulta amb nom «Course.findById».
            2. Esborrar aquest curs i totes les dades relacionades: professors, inscripcions i alumnes si escau
         */

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            TypedQuery<Course> courseQuery = em.createNamedQuery("Course.findById", Course.class);
            courseQuery.setParameter("id", 314);

            Course course = courseQuery.getSingleResult();

            em.remove(course);

            tx.commit();
        } catch (Exception e) {
            System.err.println("Good 4");
            System.err.println(e);
            //throw new RuntimeException(e);
        } finally {
            if (tx.isActive()) tx.rollback();
        }
    }

    public static void gestionarDades5() {
        /*
            1. Consulta el professor amb «id» valor 2447 fent servir la consulta amb nom «Teacher.findById».
            2. Canvia el nom d’usuari per «qres».
         */

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            TypedQuery<Teacher> teacherQuery = em.createNamedQuery("Teacher.findById", Teacher.class);
            teacherQuery.setParameter("id", 314);

            Teacher teacher = teacherQuery.getSingleResult();

            teacher.setUsername("qres2");

            em.persist(teacher);

            tx.commit();
        } catch (Exception e) {
            System.err.println("Good 5");
            System.err.println(e);
            //throw new RuntimeException(e);
        } finally {
            if (tx.isActive()) tx.rollback();
        }
    }


    public void consultaDades1(int page, String data, String text) throws Exception {
        /*
         * Pàgina 2 de la consulta dels estudiants amb última entrada («lastlogin») posterior a una data i
         * nom d’usuari que comenci per un cert text.  Els dos valors indicats per paràmetre.
         * Cal mostrar l’identificador, el nom d’usuari, el nom i cognoms, la data de la darrera entrada i el correu.
         * Ordenar per nom i cognoms ascendent.
         */


    }

    public void consultaDades2(int page, int minProfes, String categoria) {
        /*
         * Pàgina 5 de la consulta dels cursos i les qualificacions dels seus estudiants, només per a cursos
         * amb més d’un cert nombre de professors i d’una categoria determinada. Els dos valors indicats per paràmetre.
         * Cal mostrar l’identificador, la categoria i el nom complet dels cursos, i per cada alumne inscrit, el
         * seu nom, cognoms, i la qualificació.
         * Ordenar les dades per nom complet del curs ascendent i qualificació dels estudiants descendent.
         */


    }

    public void consultaDades3(int page, String text) {
        /*
         * Pàgina 1 de la consulta per cada curs del total de professors, el nombre total d’estudiants, la nota
         * mínima, mitjana i màxima d’aquests.
         * Només per a cursos de les categories que continguin un cert text indicat per paràmetre.
         * Cal mostrar l’identificador, la categoria i el nom complet dels cursos, el total de professors, el
         * nombre total d’estudiants i la nota mínima, mitjana i màxima d’aquests.
         * Ordenar pel total d’estudiants de cada curs.
         */


    }


    public String mostrarResultatConsulta(String titol, Vector<String[]> dades) {
        if (dades == null || titol == null)
            return System.lineSeparator() + "No es pot mostrar les dades de la consulta. Dades d'entrada incorrectes";

        StringBuilder resultat = new StringBuilder(System.lineSeparator() + "RESULTATS " + titol + System.lineSeparator());

        if (dades.isEmpty()) {
            resultat = new StringBuilder(titol + System.lineSeparator() + " SENSE RESULTATS " + titol + System.lineSeparator());
            return resultat.toString();
        }

        resultat.append(System.lineSeparator());

        for (int i = 0; i < dades.get(0).length; i++)
            resultat.append(StringUtils.rightPad("Camp " + (i + 1), COL_RESULTAT)).append("   ");

        resultat.append(System.lineSeparator());

        for (int i = 0; i < dades.get(0).length; i++)
            resultat.append(StringUtils.repeat("-", COL_RESULTAT)).append("   ");

        resultat.append(System.lineSeparator());

        for (String[] fila : dades) {
            for (String dada : fila) {
                resultat.append(StringUtils.rightPad(StringUtils.abbreviate(dada, COL_RESULTAT), COL_RESULTAT));
                resultat.append("   ");
            }

            resultat.append(System.lineSeparator());
        }

        return resultat + System.lineSeparator();
    }

    /**
     * Retorna una nova instancia d'EntityManager creada des de la factoria emf associada a la base de dades DATABASE
     */
    public static void initEM() {
        emf = Persistence.createEntityManagerFactory(DATABASE);
        em = emf.createEntityManager();
    }

    /**
     * Close em i emf
     */
    public static void closeDatabase() {
        em.close();
        emf.close();
    }
}
