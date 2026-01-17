public interface CourseStore {
    void save(String fileName, Course c);
    Course load(String fileName);
}
