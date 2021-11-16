package com.hannesdorfmann.mosby3.mvp;

import static junit.framework.Assert.assertEquals;

import com.hannesdorfmann.mosby3.mvp.contract.MvpView;

import org.junit.Assert;
import org.junit.Test;

/** @author Hannes Dorfmann */
public class MvpBasePresenterTest {

  @Test
  public void testAttachView() {
    MvpBasePresenter<MvpView> presenter = new MvpBasePresenter<>();
    MvpView mvpView = new MvpView() {};

    Assert.assertFalse(presenter.isViewAttached());
    presenter.attachView(mvpView);
    Assert.assertTrue(presenter.isViewAttached());
  }

  @Test
  public void testDetachView() {
    MvpBasePresenter<MvpView> presenter = new MvpBasePresenter<>();
    MvpView mvpView = new MvpView() {};

    presenter.attachView(mvpView);
    presenter.detachView();
    presenter.destroy();
    assertViewNotAttachedAndNull(presenter);
  }

  @Test
  public void testGetView() {
    MvpBasePresenter<MvpView> presenter = new MvpBasePresenter<>();
    MvpView mvpView = new MvpView() {};

    Assert.assertNull(presenter.getView());
    presenter.attachView(mvpView);
    Assert.assertNotNull(presenter.getView());
  }

  @Test
  public void testOnDestroy() {
    MvpBasePresenter<MvpView> presenter = new MvpBasePresenter<>();
    MvpView view = new MvpView() {};

    assertViewNotAttachedAndNull(presenter);
    presenter.attachView(view);

    assertViewAttachedAndNotNull(presenter);
    assertEquals(presenter.getView(), view);

    presenter.detachView();
    assertViewNotAttachedAndNull(presenter);

    presenter.attachView(view);
    presenter.detachView();
    presenter.destroy();
    assertViewNotAttachedAndNull(presenter);
  }

  private void assertViewAttachedAndNotNull(final MvpBasePresenter presenter) {
    Assert.assertTrue(presenter.isViewAttached());
    Assert.assertNotNull(presenter.getView());
  }

  private void assertViewNotAttachedAndNull(final MvpBasePresenter presenter) {
    Assert.assertFalse(presenter.isViewAttached());
    Assert.assertNull(presenter.getView());
  }
}
