package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DAY_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DAY_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_TIME_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_TIME_TWO;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_FOUR;
import static seedu.address.testutil.TypicalDeliveries.DELIVERY_THREE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.delivery.exceptions.DeliveryNotFoundException;
import seedu.address.model.delivery.exceptions.DuplicateDeliveryException;
import seedu.address.testutil.DeliveryBuilder;

public class UniqueDeliveryListTest {
    private final UniqueDeliveryList uniqueDeliveryList = new UniqueDeliveryList();

    @Test
    public void contains_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.contains(null));
    }

    @Test
    public void contains_deliveryNotInList_returnsFalse() {
        assertFalse(uniqueDeliveryList.contains(DELIVERY_THREE));
    }

    @Test
    public void contains_deliveryInList_returnsTrue() {
        uniqueDeliveryList.add(DELIVERY_THREE);
        assertTrue(uniqueDeliveryList.contains(DELIVERY_THREE));
    }

    @Test
    public void contains_deliveryWithSameIdentityFieldsInList_returnsTrue() {
        uniqueDeliveryList.add(DELIVERY_THREE);
        Delivery editedDeliveryThree = new DeliveryBuilder(DELIVERY_THREE).withDeliveryDays(VALID_DELIVERY_DAY_FIRST)
                .withDeliveryTime(VALID_DELIVERY_TIME_ONE)
                .build();
        assertTrue(uniqueDeliveryList.contains(editedDeliveryThree));
    }

    @Test
    public void add_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.add(null));
    }

    @Test
    public void add_duplicateDelivery_throwsDuplicateDeliveryException() {
        uniqueDeliveryList.add(DELIVERY_THREE);
        assertThrows(DuplicateDeliveryException.class, () -> uniqueDeliveryList.add(DELIVERY_THREE));
    }

    @Test
    public void setDelivery_nullTargetDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDelivery(null, DELIVERY_THREE));
    }

    @Test
    public void setDelivery_nullEditedDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> uniqueDeliveryList.setDelivery(DELIVERY_THREE, null));
    }

    @Test
    public void setDelivery_targetDeliveryNotInList_throwsDeliveryNotFoundException() {
        assertThrows(DeliveryNotFoundException.class, ()
                -> uniqueDeliveryList.setDelivery(DELIVERY_THREE, DELIVERY_THREE));
    }

    @Test
    public void setDelivery_editedDeliveryIsSameDelivery_success() {
        uniqueDeliveryList.add(DELIVERY_THREE);
        uniqueDeliveryList.setDelivery(DELIVERY_THREE, DELIVERY_THREE);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(DELIVERY_THREE);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDelivery_editedDeliveryHasSameIdentity_success() {
        uniqueDeliveryList.add(DELIVERY_THREE);
        Delivery editedDeliveryThree = new DeliveryBuilder(DELIVERY_THREE).withDeliveryDays(VALID_DELIVERY_DAY_SECOND)
                .withDeliveryTime(VALID_DELIVERY_TIME_TWO).build();
        uniqueDeliveryList.setDelivery(DELIVERY_THREE, editedDeliveryThree);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(editedDeliveryThree);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDelivery_editedDeliveryHasDifferentIdentity_success() {
        uniqueDeliveryList.add(DELIVERY_THREE);
        uniqueDeliveryList.setDelivery(DELIVERY_THREE, DELIVERY_FOUR);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(DELIVERY_FOUR);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDelivery_editedDeliveryHasNonUniqueIdentity_throwsDuplicateDeliveryException() {
        uniqueDeliveryList.add(DELIVERY_THREE);
        uniqueDeliveryList.add(DELIVERY_FOUR);
        assertThrows(DuplicateDeliveryException.class, ()
                -> uniqueDeliveryList.setDelivery(DELIVERY_THREE, DELIVERY_FOUR));
    }

    @Test
    public void remove_nullDelivery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.remove(null));
    }

    @Test
    public void remove_deliveryDoesNotExist_throwsDeliveryNotFoundException() {
        assertThrows(DeliveryNotFoundException.class, () -> uniqueDeliveryList.remove(DELIVERY_THREE));
    }

    @Test
    public void remove_existingDelivery_removesDelivery() {
        uniqueDeliveryList.add(DELIVERY_THREE);
        uniqueDeliveryList.remove(DELIVERY_THREE);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDeliveries_nullUniqueDeliveryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDeliveries((UniqueDeliveryList) null));
    }

    @Test
    public void setDeliveries_uniqueDeliveryList_replacesOwnListWithProvidedUniqueDeliveryList() {
        uniqueDeliveryList.add(DELIVERY_THREE);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(DELIVERY_FOUR);
        uniqueDeliveryList.setDeliveries(expectedUniqueDeliveryList);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDeliveries_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDeliveryList.setDeliveries((List<Delivery>) null));
    }

    @Test
    public void setDeliveries_list_replacesOwnListWithProvidedList() {
        uniqueDeliveryList.add(DELIVERY_THREE);
        List<Delivery> deliveryList = Collections.singletonList(DELIVERY_FOUR);
        uniqueDeliveryList.setDeliveries(deliveryList);
        UniqueDeliveryList expectedUniqueDeliveryList = new UniqueDeliveryList();
        expectedUniqueDeliveryList.add(DELIVERY_FOUR);
        assertEquals(expectedUniqueDeliveryList, uniqueDeliveryList);
    }

    @Test
    public void setDeliveries_listWithDuplicateDeliveries_throwsDuplicateDeliveryException() {
        List<Delivery> listWithDuplicateDeliveries = Arrays.asList(DELIVERY_THREE, DELIVERY_THREE);
        assertThrows(DuplicateDeliveryException.class, ()
                -> uniqueDeliveryList.setDeliveries(listWithDuplicateDeliveries));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueDeliveryList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueDeliveryList.asUnmodifiableObservableList().toString(), uniqueDeliveryList.toString());
    }
}
