package ar.unq.desapp.grupob.backenddesappapi.model

import ar.unq.desapp.grupob.backenddesappapi.helpers.OperationBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.PostBuilder
import ar.unq.desapp.grupob.backenddesappapi.helpers.UserBuilder
import ar.unq.desapp.grupob.backenddesappapi.utils.InvalidOperationException
import ar.unq.desapp.grupob.backenddesappapi.utils.InvalidUserToConfirmException
import ar.unq.desapp.grupob.backenddesappapi.utils.UserCannotBeRegisteredException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

private const val A_VALID_NAME = "a valid name"
private const val A_VALID_SURNAME = "a valid surname"
private const val A_VALID_EMAIL = "valid@asd.com"
private const val A_VALID_ADDRESS = "a valid address"
private const val A_VALID_PASSWORD = "Pass*Word"

private const val STATUS_OPERATION_ERROR = "Operation with status confirmed or cancelled can not be confirmed."

class UserEntityTest {
    @Test
    fun `when an user with invalid name is created an exception is raised`(){
        val expectedError = "The name is too short or too long"
        val excWhenTooShort = assertThrows<UserCannotBeRegisteredException> {
            UserBuilder()
                .withName("as")
                .build() }.message
        val excWhenTooLong = assertThrows<UserCannotBeRegisteredException> {
            UserBuilder()
                .withName("My name is too long to be registered in ths application")
                .build() }.message

        assertEquals(excWhenTooShort, expectedError )
        assertEquals(excWhenTooLong, expectedError )
    }
    @Test
    fun `when an user with invalid surname is created an exception is raised`(){
        var expectedError = "The surname is too short or too long"
        val builderWithName = UserBuilder().withName(A_VALID_NAME)
        val excWhenTooShort = assertThrows<UserCannotBeRegisteredException> {
            builderWithName.withSurname("as").build() }.message
        val excWhenTooLong = assertThrows<UserCannotBeRegisteredException> {
            builderWithName.withSurname("My surname is too long to be re...").build() }.message

        assertEquals(expectedError, excWhenTooShort )
        assertEquals(expectedError, excWhenTooLong )
    }
    @Test
    fun `when an user with invalid email is created an exception is raised`(){
        var expectedError = "The email does not have a valid format"
        val builderWithNameAndSurname = UserBuilder().withName(A_VALID_NAME).withSurname(A_VALID_SURNAME)

        val excWhenNotAt = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameAndSurname.withEmail("email.com").build() }.message
        val excWhenNothingAfterDot = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameAndSurname.withEmail("email@asd.").build() }.message
        val excWhenAtAndDotTogether = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameAndSurname.withEmail("email@.com").build() }.message

        assertEquals(expectedError, excWhenNotAt )
        assertEquals(expectedError, excWhenNothingAfterDot )
        assertEquals(expectedError, excWhenAtAndDotTogether )
    }
    @Test
    fun `when an user with invalid address is created an exception is raised`(){
        var expectedError = "The address is too short or too long"
        val builderWithNameSurnameAndEmail = UserBuilder()
            .withName(A_VALID_NAME).withSurname(A_VALID_SURNAME).withEmail(A_VALID_EMAIL)

        val excWhenShortAddress = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameAndEmail.withAddress("short").build()}.message
        val excWhenLargeAddress = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameAndEmail.withAddress("an address too large to be registered in this application").build()}.message

        assertEquals(expectedError, excWhenShortAddress )
        assertEquals(expectedError, excWhenLargeAddress )
    }
    @Test
    fun `when an user with invalid or null password is created, an exception is raised`(){
        val expectedError = "The password is too weak"
        val builderWithNameSurnameEmailAndAddress = UserBuilder()
            .withName(A_VALID_NAME)
            .withSurname(A_VALID_SURNAME)
            .withEmail(A_VALID_EMAIL)
            .withAddress(A_VALID_ADDRESS)

        val excWithNoLowerCase = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAndAddress.withPassword("PASS*WORD").build() }.message
        val excWithNoUpperCase = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAndAddress.withPassword("pass*word").build() }.message
        val excTooShort = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAndAddress.withPassword("Pas*").build() }.message

        assertEquals(expectedError, excWithNoLowerCase )
        assertEquals(expectedError, excWithNoUpperCase )
        assertEquals(expectedError, excTooShort )
    }
    @Test
    fun `when an user with invalid or null cvu is created, an exception is raised`(){
        val expectedError = "The cvu must have 22 digits"
        val builderWithNameSurnameEmailAddressAndPassowrd = UserBuilder()
            .withName(A_VALID_NAME)
            .withSurname(A_VALID_SURNAME)
            .withEmail(A_VALID_EMAIL)
            .withAddress(A_VALID_ADDRESS)
            .withPassword(A_VALID_PASSWORD)

        val excWhenLessThan22 = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressAndPassowrd.withCvuMP("123").build() }.message
        val excWhenMoreThan22 = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressAndPassowrd.withCvuMP("12345678901234567890123").build() }.message
        val excWhenLettersInsteadOfNumbers = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressAndPassowrd.withCvuMP("abcdefghijklmnopqrstu").build() }.message

        assertEquals(expectedError, excWhenLessThan22 )
        assertEquals(expectedError, excWhenMoreThan22 )
        assertEquals(expectedError, excWhenLettersInsteadOfNumbers )
    }
    @Test
    fun `when an user with invalid or null walletAddress is created, an exception is raised`(){
        val expectedError = "The wallet address must have 8 digits"
        val builderWithNameSurnameEmailAddressPasswordAndCvu = UserBuilder()
            .withName(A_VALID_NAME)
            .withSurname(A_VALID_SURNAME)
            .withEmail(A_VALID_EMAIL)
            .withAddress(A_VALID_ADDRESS)
            .withPassword(A_VALID_PASSWORD)
            .withCvuMP("1234567890123456789012")

        val excWhenLessThan8 = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressPasswordAndCvu.withWalletAddress("123").build() }.message
        val excWhenMoreThan8 = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressPasswordAndCvu.withWalletAddress("123456789").build() }.message
        val excWhenLettersInsteadOfNumbers = assertThrows<UserCannotBeRegisteredException> {
            builderWithNameSurnameEmailAddressPasswordAndCvu.withWalletAddress("abcdefgh").build() }.message

        assertEquals(expectedError, excWhenLessThan8 )
        assertEquals(expectedError, excWhenMoreThan8 )
        assertEquals(expectedError, excWhenLettersInsteadOfNumbers )
    }
    @Test
    fun `when a user with all valid attributes is created, no exception is raised`(){
        val validUser = UserBuilder()
            .withName(A_VALID_NAME)
            .withSurname(A_VALID_SURNAME)
            .withEmail(A_VALID_EMAIL)
            .withAddress(A_VALID_ADDRESS)
            .withPassword(A_VALID_PASSWORD)
            .withCvuMP("1234567890123456789012")
            .withWalletAddress("12345678")
            .build()
    }

    // RELATED TO OPERATIONS

    @Test
    fun `When a user tries to confirm the receipt of an operation which they do not own, an exception is raised`(){
        val otherUser: UserEntity = UserBuilder().withId(1L).build()
        val owner: UserEntity = UserBuilder().withId(2L).build()
        var post = PostBuilder()
            .withStatus(PostStatus.IN_PROGRESS)
            .withUser(otherUser)
            .build()
        val operation =
            OperationBuilder()
                .withStatus(OperationStatus.IN_PROGRESS)
                .withPost(post)
                .build()
        val errorMsg = assertThrows<InvalidUserToConfirmException> { owner.confirm(operation) }.message

        assertEquals("Only the owner can confirm this operation.", errorMsg)
    }

    @Test
    fun `when a confirmation is requested on a operation that is not in progress, an exception is raised`(){
        val client: UserEntity = UserBuilder().withId(1L).build()
        val owner: UserEntity = UserBuilder().withId(2L).build()
        var post = PostBuilder()
            .withStatus(PostStatus.IN_PROGRESS)
            .withUser(owner)
            .build()
        val closedOperation =
            OperationBuilder()
                .withStatus(OperationStatus.CLOSED)
                .withPost(post)
                .withClient(client)
                .build()
        val cancelledOperation =
            OperationBuilder()
                .withStatus(OperationStatus.CANCELLED)
                .withPost(post)
                .withClient(client)
                .build()

        val closedErrorMsg = assertThrows<InvalidOperationException> { owner.confirm(closedOperation) }.message
        val cancelledErrorMsg = assertThrows<InvalidOperationException> { owner.confirm(cancelledOperation) }.message

        assertEquals(closedErrorMsg, STATUS_OPERATION_ERROR)
        assertEquals(cancelledErrorMsg, STATUS_OPERATION_ERROR)
    }


    @Test
    fun `confirm operation`(){
        var client: UserEntity = UserBuilder().withId(1L).withSuccesfulOperations(0).build()
        var owner: UserEntity = UserBuilder().withId(2L).withScore(0).withSuccesfulOperations(0).build()
        var post = PostBuilder()
            .withStatus(PostStatus.IN_PROGRESS)
            .withUser(owner)
            .build()
        val operationScore10 =
            OperationBuilder()
                .withDateTime(LocalDateTime.now())
                .withStatus(OperationStatus.IN_PROGRESS)
                .withPost(post)
                .withClient(client)
                .build()

        val operationScore5 =
            OperationBuilder()
                .withDateTime(LocalDateTime.now().minusDays(1))
                .withStatus(OperationStatus.IN_PROGRESS)
                .withPost(post)
                .withClient(client)
                .build()

        owner.confirm(operationScore10)
        owner.confirm(operationScore5)

        assertEquals(owner.score, 15)
        assertEquals(client.score, 15)
        assertEquals(owner.successfulOperation, 2)
        assertEquals(client.successfulOperation, 2)
    }

}