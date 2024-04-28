package ar.unq.desapp.grupob.backenddesappapi.model

import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.utils.UserCannotBeRegisteredException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserEntityTest {

    @Test
    fun `when an user with invalid name is created an exception is raised`(){
        val expectedError = "The name is too short or too long"
        val excWhenNull = assertThrows<UserCannotBeRegisteredException> { UserBuilder().build() }.message
        val excWhenTooShort = assertThrows<UserCannotBeRegisteredException> {
            UserBuilder()
                .withName("as")
                .build() }.message
        val excWhenTooLong = assertThrows<UserCannotBeRegisteredException> {
            UserBuilder()
                .withName("My name is too long to be registered in ths application")
                .build() }.message

        assertEquals(excWhenNull, expectedError )
        assertEquals(excWhenTooShort, expectedError )
        assertEquals(excWhenTooLong, expectedError )
    }

    @Test
    fun `when an user with invalid surname is created an exception is raised`(){
        var expectedError = "The surname is too short or too long"
        val builderWithName = UserBuilder().withName("a valid name")

        val excWhenNull = assertThrows<UserCannotBeRegisteredException> {
            builderWithName.build() }.message
        val excWhenTooShort = assertThrows<UserCannotBeRegisteredException> {
            builderWithName.withSurname("as").build() }.message
        val excWhenTooLong = assertThrows<UserCannotBeRegisteredException> {
            builderWithName.withSurname("My surname is too long to be re...").build() }.message

        assertEquals(expectedError, excWhenNull )
        assertEquals(expectedError, excWhenTooShort )
        assertEquals(expectedError, excWhenTooLong )
    }

    @Test
    fun `when an user with invalid email is created an exception is raised`(){
        var expectedError = "The email does not have a valid format"
        val builderWithNameAndSurname = UserBuilder().withName("a valid name").withSurname("a valid surname")

        val excWhenNull = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameAndSurname.build() }.message
        val excWhenNotAt = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameAndSurname.withEmail("email.com").build() }.message
        val excWhenNothingAfterDot = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameAndSurname.withEmail("email@asd.").build() }.message
        val excWhenAtAndDotTogether = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameAndSurname.withEmail("email@.com").build() }.message

        assertEquals(expectedError, excWhenNull )
        assertEquals(expectedError, excWhenNotAt )
        assertEquals(expectedError, excWhenNothingAfterDot )
        assertEquals(expectedError, excWhenAtAndDotTogether )
    }

    @Test
    fun `when an user with invalid address is created an exception is raised`(){
        var expectedError = "The address is too short or too long"
        val builderWithNameSurnameAndEmail = UserBuilder()
            .withName("a valid name").withSurname("a valid surname").withEmail("valid@asd.com")

        val excWhenNull = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameAndEmail.build() }.message
        val excWhenShortAddress = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameAndEmail.withAddress("short").build()}.message
        val excWhenLargeAddress = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameAndEmail.withAddress("an address too large to be registered in this application").build()}.message

        assertEquals(expectedError, excWhenNull )
        assertEquals(expectedError, excWhenShortAddress )
        assertEquals(expectedError, excWhenLargeAddress )
    }

    @Test
    fun `when an user with invalid or null password is created, an exception is raised`(){
        val expectedError = "The password is too weak"
        val builderWithNameSurnameEmailAndAddress = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid surname")
            .withEmail("valid@asd.com")
            .withAddress("a valid address")

        val excWhenNull = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAndAddress.build() }.message
        val excWithNoLowerCase = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAndAddress.withPassword("PASS*WORD").build() }.message
        val excWithNoUpperCase = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAndAddress.withPassword("pass*word").build() }.message
        val excTooShort = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAndAddress.withPassword("Pas*").build() }.message

        assertEquals(expectedError, excWhenNull )
        assertEquals(expectedError, excWithNoLowerCase )
        assertEquals(expectedError, excWithNoUpperCase )
        assertEquals(expectedError, excTooShort )
    }

    @Test
    fun `when an user with invalid or null cvu is created, an exception is raised`(){
        val expectedError = "The cvu must have 22 digits"
        val builderWithNameSurnameEmailAddressAndPassowrd = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid surname")
            .withEmail("valid@asd.com")
            .withAddress("a valid address")
            .withPassword("Pass*Word")

        val excWhenNull = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressAndPassowrd.build() }.message
        val excWhenLessThan22 = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressAndPassowrd.withCvuMP("123").build() }.message
        val excWhenMoreThan22 = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressAndPassowrd.withCvuMP("12345678901234567890123").build() }.message
        val excWhenLettersInsteadOfNumbers = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressAndPassowrd.withCvuMP("abcdefghijklmnopqrstu").build() }.message

        assertEquals(expectedError, excWhenNull )
        assertEquals(expectedError, excWhenLessThan22 )
        assertEquals(expectedError, excWhenMoreThan22 )
        assertEquals(expectedError, excWhenLettersInsteadOfNumbers )
    }

    @Test
    fun `when an user with invalid or null walletAddress is created, an exception is raised`(){
        val expectedError = "The wallet address must have 8 digits"
        val builderWithNameSurnameEmailAddressPasswordAndCvu = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid surname")
            .withEmail("valid@asd.com")
            .withAddress("a valid address")
            .withPassword("Pass*Word")
            .withCvuMP("1234567890123456789012")

        val excWhenNull = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressPasswordAndCvu.build() }.message
        val excWhenLessThan8 = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressPasswordAndCvu.withWalletAddress("123").build() }.message
        val excWhenMoreThan8 = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressPasswordAndCvu.withWalletAddress("123456789").build() }.message
        val excWhenLettersInsteadOfNumbers = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressPasswordAndCvu.withWalletAddress("abcdefgh").build() }.message

        assertEquals(expectedError, excWhenNull )
        assertEquals(expectedError, excWhenLessThan8 )
        assertEquals(expectedError, excWhenMoreThan8 )
        assertEquals(expectedError, excWhenLettersInsteadOfNumbers )
    }

    @Test
    fun `when a user with all valid attributes is created, no exception is raised`(){
        val validUser = UserBuilder()
            .withName("a valid name")
            .withSurname("a valid surname")
            .withEmail("valid@asd.com")
            .withAddress("a valid address")
            .withPassword("Pass*Word")
            .withCvuMP("1234567890123456789012")
            .withWalletAddress("12345678")
            .build()
    }
}