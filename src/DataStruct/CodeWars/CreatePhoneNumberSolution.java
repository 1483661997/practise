package DataStruct.CodeWars;

public class CreatePhoneNumberSolution {

    public static void main(String[] args) {
        System.out.println(createPhoneNumber(new int[]{1,2,3,4,5,6,7,8,9,0})
        );
    }
    public static String createPhoneNumber(int[] numbers) {
        // Your code here!
        StringBuilder str = new StringBuilder();
        str.append("(");
        for(int i = 0 ; i < 3; i++){
            str.append(numbers[i]);
        }
        str.append(") ");
        for(int i = 3; i < numbers.length; i++){
            str.append(numbers[i]);
        }

        str.insert(9, '-');
        return str.toString();
    }

}
